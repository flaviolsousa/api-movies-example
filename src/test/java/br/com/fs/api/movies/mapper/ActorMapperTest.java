package br.com.fs.api.movies.mapper;

import br.com.fs.api.movies.model.mapper.ActorMapper;
import br.com.fs.api.movies.model.mapper.ActorMapperImpl;
import br.com.fs.api.movies.templates.dto.ActorDtoTemplate;
import br.com.fs.api.movies.templates.model.ActorTemplate;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ActorMapperTest {

  private final ActorDtoTemplate actorDtoTemplate = ActorDtoTemplate.getInstance();
  private final ActorTemplate actorTemplate = ActorTemplate.getInstance();

  private final ActorMapper mapper = new ActorMapperImpl();

  @Test
  public void testToModel() {
    Assertions.assertThat(mapper.toModel(null)).isNull();

    var dto = actorDtoTemplate.getValid();
    var model = mapper.toModel(dto);

    Assertions.assertThat(model)
      .hasFieldOrPropertyWithValue("name", dto.getName());
  }

  @Test
  public void testToDto() {
    Assertions.assertThat(mapper.toDto(null)).isNull();

    var model = actorTemplate.getValid();
    var dto = mapper.toDto(model);

    Assertions.assertThat(dto)
      .hasFieldOrPropertyWithValue("name", model.getName());
  }
}
