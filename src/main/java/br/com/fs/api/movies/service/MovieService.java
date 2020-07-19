package br.com.fs.api.movies.service;

import br.com.fs.api.movies.exception.ApiMovieValidationException;
import br.com.fs.api.movies.model.Actor;
import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepository movieRepository;

  public List<Movie> findByCensorship(Censorship censorship) {
    return movieRepository.findByCensorship(censorship);
  }

  public Movie save(Movie movie) {
    this.checkIfAllowedSave(movie);
    final var db = movieRepository.save(movie);
    return db;
  }

  private void checkIfAllowedSave(Movie movie) {
    var dbMovie = this.isAlreadyRegistered(movie);
    if (dbMovie != null && dbMovie.getId().equals(movie.getId())) {
      var message = String.format("The movie is a duplicate of '%s' (id: '%s')", dbMovie.getName(), dbMovie.getId());
      throw new ApiMovieValidationException(message);
    }
    var countDistinct = movie.getCast().stream().map(Actor::getName).distinct().count();
    if (countDistinct != movie.getCast().size()) {
      throw new ApiMovieValidationException("Contains one or more actors more than once in the movie cast");
    }
  }

  private Movie isAlreadyRegistered(Movie movie) {
    String regex = movie.getName().replaceAll("\\s+", "\\\\s+");
    return movieRepository.findByNameRegex(regex).orElse(null);
  }

}
