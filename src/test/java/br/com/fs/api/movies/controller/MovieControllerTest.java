package br.com.fs.api.movies.controller;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.controller.impl.MovieControllerImpl;
import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.model.mapper.MovieMapper;
import br.com.fs.api.movies.service.MovieService;
import br.com.fs.api.movies.templates.dto.MovieDtoTemplate;
import br.com.fs.api.movies.templates.model.MovieTemplate;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MovieController.class, MovieControllerImpl.class})
@AutoConfigureMockMvc()
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Slf4j
public class MovieControllerTest {

  public static final String BASE_URL = "http://localhost:8080/movies";

  private final TestUtil testUtil = TestUtil.getInstance();

  private final MovieTemplate movieTemplate = MovieTemplate.getInstance();
  private final MovieDtoTemplate movieDtoTemplate = MovieDtoTemplate.getInstance();

  @MockBean
  private MovieService movieService;

  @MockBean
  private MovieMapper movieMapper;

  @Autowired
  protected MockMvc mvc;

  @Test
  public void testFindByCensorship() throws Exception {
    int amount = 3;
    var movieExample = Movie.builder().censorship(Censorship.CENSORED).build();
    var movies = testUtil.gimme(amount, movieTemplate::getValid);
    var moviesDto = testUtil.gimme(amount, movieDtoTemplate::getValid);

    given(movieService.getByExample(movieExample)).willReturn(movies);
    given(movieMapper.toDto(any(List.class))).willReturn(moviesDto);

    final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
      .get(BASE_URL + "?censorship=CENSORED")
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andReturn();

    final String response = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.length()", Matchers.equalTo(amount)));
    for (int i = 0; i < amount; i++) {
      assertThat(response, hasJsonPath(String.format("$[%d].id", i), Matchers.equalTo(moviesDto.get(i).getId())));
      assertThat(response, hasJsonPath(String.format("$[%d].name", i), Matchers.equalTo(moviesDto.get(i).getName())));
    }

  }

  @Test
  public void testCreate() throws Exception {
    var movie = movieTemplate.getValid();
    var movieDto = movieDtoTemplate.getNew();

    given(movieService.save(movie)).willReturn(movie);
    given(movieMapper.toDto(movie)).willReturn(movieDto);
    given(movieMapper.toModel(movieDto)).willReturn(movie);

    final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isCreated())
      .andReturn();

    final String response = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.id", Matchers.equalTo(movieDto.getId())));
  }

  @Test
  public void testCreateInputValidation() throws Exception {
    var movieDto = movieDtoTemplate.getWithoutLists();

    final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isBadRequest())
      .andReturn();

    final String response = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.violations.length()", Matchers.greaterThan(1)));
  }

  @Test
  public void testUpdate() throws Exception {
    var movie = movieTemplate.getValid();
    var movieDto = movieDtoTemplate.getValid();

    given(movieService.save(movie)).willReturn(movie);
    given(movieMapper.toDto(movie)).willReturn(movieDto);
    given(movieMapper.toModel(movieDto)).willReturn(movie);

    final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
      .put(BASE_URL + "/" + movieDto.getId())
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andReturn();

    final String response = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.id", Matchers.equalTo(movieDto.getId())));
  }

  @Test
  public void testUpdateInputValidation() throws Exception {
    var movieDto = movieDtoTemplate.getWithoutLists();
    String response = mvc.perform(MockMvcRequestBuilders
      .put(BASE_URL + "/" + movieDto.getId())
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
  public void testCreateWithIdNotNull() throws Exception {
    var movieDto = movieDtoTemplate.getValid();

    final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
      .post(BASE_URL)
      .content(testUtil.toJson(movieDto))
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isBadRequest())
      .andReturn();

    final String response = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
    log.info(response);

    assertThat(response, hasJsonPath("$", Matchers.notNullValue()));
    assertThat(response, hasJsonPath("$.message", Matchers.containsString("id")));
  }

}
