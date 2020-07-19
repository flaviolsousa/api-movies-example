package br.com.fs.api.movies.templates.model;

import br.com.fs.api.movies.templates.BaseTemplate;
import br.com.fs.api.movies.model.Director;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectorTemplate extends BaseTemplate {

  @Getter
  private static final DirectorTemplate instance = new DirectorTemplate();

  public Director getValid() {
    return Director.builder()
      .name(faker.name().fullName())
      .build();
  }

}
