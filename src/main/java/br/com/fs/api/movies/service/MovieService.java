package br.com.fs.api.movies.service;

import br.com.fs.api.movies.exception.ApiMovieValidationException;
import br.com.fs.api.movies.model.Actor;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;

  public List<Movie> getByExample(Movie example) {
    var matcher = getDefaultExampleMatcher();
    var searchExample = Example.of(example, matcher);
    return movieRepository.findAll(searchExample);
  }

  private ExampleMatcher getDefaultExampleMatcher() {
    return ExampleMatcher
      .matching()
      .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
      .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
      .withMatcher("censorship", ExampleMatcher.GenericPropertyMatchers.exact());
  }

  public Movie save(Movie movie) {
    this.checkIfAllowedSave(movie);
    var db = movieRepository.save(movie);
    return db;
  }

  private void checkIfAllowedSave(Movie movie) {
    this.checkIsUpdate(movie);
    this.checkAlreadyRegistered(movie);
    this.checkDuplicatedActor(movie);
  }

  private void checkIsUpdate(Movie movie) {
    if (movie.getId() != null) {
      movieRepository.findById(movie.getId())
        .orElseThrow(() ->
          new ApiMovieValidationException("'id' not founded"));
    }
  }

  private void checkAlreadyRegistered(Movie movie) {
    String regex = "^" + movie.getName().replaceAll("\\s+", "\\\\s+") + "$";
    var dbMovie = movieRepository.findByNameRegex(regex).orElse(null);
    if (dbMovie != null && !dbMovie.getId().equals(movie.getId())) {
      var message = String.format("The movie is a duplicate of '%s' (id: '%s')", dbMovie.getName(), dbMovie.getId());
      throw new ApiMovieValidationException(message);
    }
  }

  private void checkDuplicatedActor(Movie movie) {
    var countDistinct = movie.getCast().stream().map(Actor::getName).distinct().count();
    if (countDistinct != movie.getCast().size()) {
      throw new ApiMovieValidationException("Contains one or more actors more than once in the movie cast");
    }
  }

}
