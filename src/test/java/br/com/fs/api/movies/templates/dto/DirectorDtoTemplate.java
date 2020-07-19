package br.com.fs.api.movies.templates.dto;

import br.com.fs.api.movies.templates.BaseTemplate;
import br.com.fs.api.movies.model.Director;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectorDtoTemplate extends BaseTemplate {

  @Getter
  private static final DirectorDtoTemplate instance = new DirectorDtoTemplate();

  public Director getValid() {
    return Director.builder()
      .name(faker.name().fullName())
      .build();
  }

}
