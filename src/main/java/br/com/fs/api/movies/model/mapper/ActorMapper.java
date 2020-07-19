package br.com.fs.api.movies.model.mapper;

import br.com.fs.api.movies.model.Actor;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.model.dto.ActorDto;
import br.com.fs.api.movies.model.dto.MovieDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorMapper {

  ActorDto toDto(Actor movie);

  Actor toModel(ActorDto movie);

}
