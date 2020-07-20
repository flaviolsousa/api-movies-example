package br.com.fs.api.movies.templates.dto.filters;

import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.dto.filter.MovieFilterDto;
import br.com.fs.api.movies.templates.BaseTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieFilterDtoTemplate extends BaseTemplate {

  @Getter
  private static final MovieFilterDtoTemplate instance = new MovieFilterDtoTemplate();
  
  public MovieFilterDto getValid() {
    return MovieFilterDto.builder()
      .censorship(faker.options().option(Censorship.values()))
      .build();
  }

}
