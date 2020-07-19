package br.com.fs.api.movies.templates.dto;

import br.com.fs.api.movies.templates.BaseTemplate;
import br.com.fs.api.movies.model.Actor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorDtoTemplate extends BaseTemplate {

  @Getter
  private static final ActorDtoTemplate instance = new ActorDtoTemplate();

  public Actor getValid() {
    return Actor.builder()
      .name(faker.name().fullName())
      .build();
  }

}
