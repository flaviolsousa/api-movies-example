package br.com.fs.api.movies.model.mapper;

import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.model.dto.MovieDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ActorMapper.class, DirectorMapper.class})
public interface MovieMapper {

  MovieDto toDto(Movie movie);

  List<MovieDto> toDto(List<Movie> movie);

  Movie toModel(MovieDto movie);

}
