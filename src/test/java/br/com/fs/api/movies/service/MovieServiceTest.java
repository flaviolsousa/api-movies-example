package br.com.fs.api.movies.service;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.exception.ApiMovieValidationException;
import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.repository.MovieRepository;
import br.com.fs.api.movies.templates.model.MovieTemplate;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

  private final TestUtil testUtil = TestUtil.getInstance();

  private final MovieTemplate movieTemplate = MovieTemplate.getInstance();

  @InjectMocks
  private MovieService movieService;

  @Mock
  private MovieRepository movieRepository;

  @Test
  public void testFindByCensorship() {
    int amount = 3;
    var movies = testUtil.gimme(amount, movieTemplate::getValid);
    when(movieRepository.findByCensorship(any(Censorship.class))).thenReturn(movies);

    var result = movieService.findByCensorship(Censorship.CENSORED);
    assertThat(result, notNullValue());
    assertThat(result.size(), equalTo(amount));
  }

  @Test
  public void testFindByCensorshipNotFounded() {
    var movies = new ArrayList<Movie>();
    when(movieRepository.findByCensorship(any(Censorship.class))).thenReturn(movies);

    var result = movieService.findByCensorship(Censorship.CENSORED);
    assertThat(result, notNullValue());
    assertThat(result.size(), equalTo(0));
  }

  @Test
  public void testSave() {
    var movie = movieTemplate.getNew();
    var captor = ArgumentCaptor.forClass(Movie.class);
    when(movieRepository.save(any(Movie.class))).thenReturn(movie);

    var result = movieService.save(movie);

    verify(movieRepository, times(1)).save(captor.capture());
    assertThat(captor.getValue(), equalTo(movie));
    assertThat(result, equalTo(movie));
  }

  @Test(expected = ApiMovieValidationException.class)
  public void testSaveDuplicated() {
    var movie = movieTemplate.getNew();
    var dbMovie = movieTemplate.getValid();
    dbMovie.setName(movie.getName());
    when(movieRepository.findByNameRegex(any(String.class))).thenReturn(Optional.of(dbMovie));

    movieService.save(dbMovie);
  }

  @Test(expected = ApiMovieValidationException.class)
  public void testSaveDuplicatedActor() {
    var movie = movieTemplate.getNew();
    var dbMovie = movieTemplate.getValid();
    movie.getCast().add(movie.getCast().get(0));
    when(movieRepository.findByNameRegex(any(String.class))).thenReturn(Optional.of(dbMovie));

    movieService.save(movie);
  }

}
