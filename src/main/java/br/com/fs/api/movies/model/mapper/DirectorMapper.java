package br.com.fs.api.movies.model.mapper;

import br.com.fs.api.movies.model.Director;
import br.com.fs.api.movies.model.dto.DirectorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

  DirectorDto toDto(Director director);

  Director toModel(DirectorDto director);

}
