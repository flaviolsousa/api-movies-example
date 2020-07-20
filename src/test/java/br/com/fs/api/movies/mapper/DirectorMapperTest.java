package br.com.fs.api.movies.mapper;

import br.com.fs.api.movies.model.mapper.DirectorMapper;
import br.com.fs.api.movies.model.mapper.DirectorMapperImpl;
import br.com.fs.api.movies.templates.dto.DirectorDtoTemplate;
import br.com.fs.api.movies.templates.model.DirectorTemplate;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class DirectorMapperTest {

  private final DirectorDtoTemplate actorDtoTemplate = DirectorDtoTemplate.getInstance();
  private final DirectorTemplate actorTemplate = DirectorTemplate.getInstance();

  private final DirectorMapper mapper = new DirectorMapperImpl();

  @Test
  public void testToModel() {
    var dto = actorDtoTemplate.getValid();
    var model = mapper.toModel(dto);

    Assertions.assertThat(model)
      .hasFieldOrPropertyWithValue("name", dto.getName());
  }

  @Test
  public void testToDto() {
    var model = actorTemplate.getValid();
    var dto = mapper.toDto(model);

    Assertions.assertThat(dto)
      .hasFieldOrPropertyWithValue("name", model.getName());
  }
}
