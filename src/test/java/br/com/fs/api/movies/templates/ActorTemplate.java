package br.com.fs.api.movies.templates;

import br.com.fs.api.movies.BaseTemplate;
import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.model.Actor;
import lombok.Getter;

public class ActorTemplate extends BaseTemplate {

  @Getter
  private static final ActorTemplate instance = TestUtil.newInstance(ActorTemplate.class);

  public Actor getValid() {
    return Actor.builder()
      .name(faker.rickAndMorty().character())
      .build();
  }

}
