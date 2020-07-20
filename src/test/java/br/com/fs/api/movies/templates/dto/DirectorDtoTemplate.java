package br.com.fs.api.movies.templates.dto;

import br.com.fs.api.movies.model.dto.DirectorDto;
import br.com.fs.api.movies.templates.BaseTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectorDtoTemplate extends BaseTemplate {

  @Getter
  private static final DirectorDtoTemplate instance = new DirectorDtoTemplate();

  public DirectorDto getValid() {
    return DirectorDto.builder()
      .name(faker.name().fullName())
      .build();
  }

}
