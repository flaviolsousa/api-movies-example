package br.com.fs.api.movies.service;

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

  public Movie save(Movie movie) {
    if (isAllowedToSave(movie)) {
      //throw new ApiMovieException("The movie has c"); // @TODO fixme
    }
    return movieRepository.save(movie); // @TODO validate actors
  }

  private boolean isAllowedToSave(Movie movie) {
    String regex = movie.getName(); // @Fixme
    return movieRepository.findByNameRegex(regex).isPresent();
  }

  public List<Movie> findByCensorship(Censorship censorship) {
    return movieRepository.findByCensorship(censorship);
  }


}
