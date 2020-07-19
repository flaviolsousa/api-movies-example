package br.com.fs.api.movies.templates;

import br.com.fs.api.movies.BaseTemplate;
import br.com.fs.api.movies.model.Director;
import org.springframework.stereotype.Component;

public class DirectorTemplate extends BaseTemplate {

  public Director getValid() {
    return Director.builder()
      .name(faker.rickAndMorty().character())
      .build();
  }

}
