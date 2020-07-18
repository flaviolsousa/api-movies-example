package br.com.fs.api.movies.controller;

import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.model.error.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Movies", tags = "Movies")
@RequestMapping("/movies")
public interface MovieController {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a new Movie", response = MovieDto.class)
  @ApiResponses({
    @ApiResponse(code = 201, message = "Created", response = MovieDto.class),
    @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)
  })
  MovieDto createMovie(@Valid @RequestBody MovieDto request) throws Exception;


  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Retrieve a list of Movies", response = MovieDto.class)
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK", response = MovieDto.class, responseContainer = "List"),
    @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class)
  })
  List<MovieDto> findMovies(final @RequestParam Boolean censorship) throws Exception;

}
