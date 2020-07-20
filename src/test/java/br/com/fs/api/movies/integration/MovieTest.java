package br.com.fs.api.movies.integration;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.config.HooksConfig;
import br.com.fs.api.movies.controller.MovieController;
import br.com.fs.api.movies.controller.impl.MovieControllerImpl;
import br.com.fs.api.movies.model.Censorship;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  MovieRepository.class,
  HooksConfig.class
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

  private List<MovieDto> create(int amountCensored, int amountUncensored) throws Exception {
    List<MovieDto> censored = testUtil.gimme(amountCensored, movieDtoTemplate::getNewCensored);
    List<MovieDto> uncensored = testUtil.gimme(amountUncensored, movieDtoTemplate::getNewUncensored);
    List<MovieDto> movies = Stream.concat(censored.stream(), uncensored.stream()).collect(Collectors.toList());

    for (MovieDto movie : movies) {
      create(movie);
    }
    return movies;
  }

  private MovieDto[] find(MovieFilterDto filterDto) throws Exception {
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

    MovieDto[] responseDto = testUtil.toObject(response, MovieDto[].class);

    assertThat(responseDto, Matchers.notNullValue());
    for (MovieDto dto : responseDto) {
      assertThat(dto.getId(), Matchers.notNullValue());
      assertThat(dto.getName(), Matchers.notNullValue());
    }

    return responseDto;
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

  @Test
  public void testFind() throws Exception {
    List<MovieDto> moviesDto = create(3, 3);
    MovieDto[] resultDto = find(MovieFilterDto.builder().build());

    assertThat(resultDto.length, Matchers.equalTo(6));
  }

  @Test
  public void testFindByCensorship() throws Exception {
    create(3, 2);

    MovieDto[] resultDto1 = find(MovieFilterDto.builder().censorship(Censorship.CENSORED).build());
    assertThat(resultDto1.length, Matchers.equalTo(3));

    MovieDto[] resultDto2 = find(MovieFilterDto.builder().censorship(Censorship.UNCENSORED).build());
    assertThat(resultDto2.length, Matchers.equalTo(2));
  }

  @Test
  public void testFindUncensored() throws Exception {
    List<MovieDto> moviesDto = create(3, 2);

  }

  @Test
  public void testFindEmpty() throws Exception {
    MovieDto[] moviesDto = find(MovieFilterDto.builder().build());
    assertThat(moviesDto.length, Matchers.equalTo(0));
  }

}
