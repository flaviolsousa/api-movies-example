package br.com.fs.api.movies.controller.impl;

import br.com.fs.api.movies.controller.MovieController;
import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.model.mapper.MovieMapper;
import br.com.fs.api.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieControllerImpl implements MovieController {

  private final MovieMapper movieMapper;
  private final MovieService movieService;

  @Override
  public MovieDto save(@Valid MovieDto request) {
    var movie = movieMapper.toModel(request);
    movie = movieService.save(movie);
    return movieMapper.toDto(movie);
  }

  @Override
  public List<MovieDto> findMovies(Censorship censorship) {
    var movies = movieService.findByCensorship(censorship);
    return movieMapper.toDto(movies);
  }
}
