package br.com.fs.api.movies.integration;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.controller.MovieController;
import br.com.fs.api.movies.controller.impl.MovieControllerImpl;
import br.com.fs.api.movies.model.mapper.ActorMapperImpl;
import br.com.fs.api.movies.model.mapper.DirectorMapperImpl;
import br.com.fs.api.movies.model.mapper.MovieMapperImpl;
import br.com.fs.api.movies.repository.MovieRepository;
import br.com.fs.api.movies.service.MovieService;
import br.com.fs.api.movies.templates.dto.MovieDtoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
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

  @Before
  public void before() throws Exception {
    this.movieRepository.deleteAll();
  }

  @Test
  public void testSave() throws Exception {
    var movieDto = movieDtoTemplate.getNew();

    final String response = mvc.perform(MockMvcRequestBuilders
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
    assertThat(response, hasJsonPath("$.name", Matchers.equalTo(movieDto.getName())));
  }

  @Test
  public void testSaveDuplicated() throws Exception {
    var movieDto = movieDtoTemplate.getNewFixedName();

    final String response1 = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());

    log.info(response1);

    assertThat(response1, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response1, hasJsonPath("$.id", Matchers.notNullValue()));
    assertThat(response1, hasJsonPath("$.name", Matchers.equalTo(movieDto.getName())));

    final String response2 = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isBadRequest())
      .andReturn()
      .getResponse()
      .getContentAsString(Charset.defaultCharset());

    log.info(response2);
    assertThat(response2, hasJsonPath("$.message", Matchers.containsString("duplicate")));

  }

}
