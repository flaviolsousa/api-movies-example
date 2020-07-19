package br.com.fs.api.movies.templates.model;

import br.com.fs.api.movies.templates.BaseTemplate;
import br.com.fs.api.movies.model.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorTemplate extends BaseTemplate {

  @Getter
  private static final ActorTemplate instance = new ActorTemplate();

  public Actor getValid() {
    return Actor.builder()
      .name(faker.name().fullName())
      .build();
  }

}
