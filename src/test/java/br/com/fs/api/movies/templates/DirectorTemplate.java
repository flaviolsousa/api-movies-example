package br.com.fs.api.movies.templates;

import br.com.fs.api.movies.BaseTemplate;
import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.model.Director;
import lombok.Getter;

public class DirectorTemplate extends BaseTemplate {

  @Getter
  private static final DirectorTemplate instance = TestUtil.newInstance(DirectorTemplate.class);

  public Director getValid() {
    return Director.builder()
      .name(faker.rickAndMorty().character())
      .build();
  }

}
