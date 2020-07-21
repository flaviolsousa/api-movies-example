package br.com.fs.api.movies.controller.impl;

import br.com.fs.api.movies.controller.MovieController;
import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.model.dto.filter.MovieFilterDto;
import br.com.fs.api.movies.model.mapper.MovieMapper;
import br.com.fs.api.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieControllerImpl implements MovieController {

  private final MovieMapper movieMapper;
  private final MovieService movieService;

  @Override
  public MovieDto update(MovieDto request, String id) {
    request.setId(id);
    var movie = movieMapper.toModel(request);
    movie = movieService.save(movie);
    return movieMapper.toDto(movie);
  }

  @Override
  public MovieDto create(MovieDto request) {
    if (request.getId() != null)
      throw new IllegalArgumentException("You cannot set id int new new document");

    var movie = movieMapper.toModel(request);
    movie = movieService.save(movie);
    return movieMapper.toDto(movie);
  }

  @Override
  public List<MovieDto> findMovies(MovieFilterDto filter) {
    var movie = movieMapper.toModel(filter);
    var movies = movieService.getByExample(movie);
    return movieMapper.toDto(movies);
  }
}
