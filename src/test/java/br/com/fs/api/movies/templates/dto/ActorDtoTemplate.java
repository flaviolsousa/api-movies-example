package br.com.fs.api.movies.templates.dto;

import br.com.fs.api.movies.model.dto.ActorDto;
import br.com.fs.api.movies.templates.BaseTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorDtoTemplate extends BaseTemplate {

  @Getter
  private static final ActorDtoTemplate instance = new ActorDtoTemplate();

  public ActorDto getValid() {
    return ActorDto.builder()
      .name(faker.name().fullName() + faker.name().lastName())
      .build();
  }

}
