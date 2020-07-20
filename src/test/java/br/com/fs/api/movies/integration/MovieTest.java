package br.com.fs.api.movies.integration;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.controller.MovieController;
import br.com.fs.api.movies.controller.impl.MovieControllerImpl;
import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.model.dto.filter.MovieFilterDto;
import br.com.fs.api.movies.model.mapper.ActorMapperImpl;
import br.com.fs.api.movies.model.mapper.DirectorMapperImpl;
import br.com.fs.api.movies.model.mapper.MovieMapperImpl;
import br.com.fs.api.movies.repository.MovieRepository;
import br.com.fs.api.movies.service.MovieService;
import br.com.fs.api.movies.templates.dto.MovieDtoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.Objects;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
  MovieController.class,
  MovieControllerImpl.class,
  MovieMapperImpl.class,
  ActorMapperImpl.class,
  DirectorMapperImpl.class,
  MovieService.class,
  MovieRepository.class
})
@AutoConfigureMockMvc()
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureDataMongo
@Slf4j
@FixMethodOrder(MethodSorters.JVM)
public class MovieTest {

  public static final String BASE_URL = "http://localhost:8080/movies";

  private final TestUtil testUtil = TestUtil.getInstance();

  private final MovieDtoTemplate movieDtoTemplate = MovieDtoTemplate.getInstance();

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected MovieRepository movieRepository;

  @BeforeEach
  public void before() {
    this.movieRepository.deleteAll();
  }

  private MovieDto create(MovieDto movieDto) throws Exception {
    var response = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.id", Matchers.notNullValue()));

    MovieDto responseDto = testUtil.toObject(response, MovieDto.class);

    assertThat(responseDto, Matchers.notNullValue());
    assertThat(responseDto.getId(), Matchers.notNullValue());
    assertThat(responseDto.getName(), Matchers.equalTo(movieDto.getName()));

    return responseDto;
  }

  private String find(MovieFilterDto filterDto) throws Exception {
    var response = mvc.perform(MockMvcRequestBuilders
      .get(BASE_URL +
        "?censorship=" + Objects.requireNonNullElse(filterDto.getCensorship(), "")
      )
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());
    log.info(response);
    return response;
  }

  @Test
  public void testCreate() throws Exception {
    var movieDto = movieDtoTemplate.getNew();
    this.create(movieDto);
  }

  @Test
  public void testCreateDuplicated() throws Exception {
    var movieDto = movieDtoTemplate.getNewFixedName();
    this.create(movieDto);

    final String response = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isBadRequest())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());

    log.info(response);
    assertThat(response, hasJsonPath("$.message", Matchers.containsString("duplicate")));
  }

  @Test
  public void testUpdate() throws Exception {
    var movieDto = movieDtoTemplate.getNew();
    var createdDto = this.create(movieDto);

    movieDto = movieDtoTemplate.getValid();
    movieDto.setId(createdDto.getId());

    var response = mvc.perform(MockMvcRequestBuilders
      .put(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.id", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.name", Matchers.equalTo(movieDto.getName())));
  }

  @Test
  public void testUpdateInvalidId() throws Exception {
    var movieDto = movieDtoTemplate.getValid();

    var response = mvc.perform(MockMvcRequestBuilders
      .put(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isBadRequest())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.message", Matchers.notNullValue()));
  }


}
