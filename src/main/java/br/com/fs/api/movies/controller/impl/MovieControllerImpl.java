package br.com.fs.api.movies.controller.impl;

import br.com.fs.api.movies.controller.MovieController;
import br.com.fs.api.movies.model.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieControllerImpl implements MovieController {

  @Override
  public MovieDto createMovie(@Valid MovieDto request) throws Exception {
    return null;
  }

  @Override
  public List<MovieDto> findMovies(Boolean censorship) throws Exception {
    return null;
  }
}
