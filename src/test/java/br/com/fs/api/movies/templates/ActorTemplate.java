package br.com.fs.api.movies.templates;

import br.com.fs.api.movies.BaseTemplate;
import br.com.fs.api.movies.model.Actor;

public class ActorTemplate extends BaseTemplate {

  public Actor getValid() {
    return Actor.builder()
      .name(faker.rickAndMorty().character())
      .build();
  }

}
