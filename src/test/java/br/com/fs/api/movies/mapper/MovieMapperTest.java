package br.com.fs.api.movies.mapper;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.model.mapper.ActorMapper;
import br.com.fs.api.movies.model.mapper.DirectorMapper;
import br.com.fs.api.movies.model.mapper.MovieMapperImpl;
import br.com.fs.api.movies.templates.dto.MovieDtoTemplate;
import br.com.fs.api.movies.templates.model.MovieTemplate;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieMapperTest {

  private final MovieDtoTemplate movieDtoTemplate = MovieDtoTemplate.getInstance();
  private final MovieTemplate movieTemplate = MovieTemplate.getInstance();

  private final TestUtil testUtil = TestUtil.getInstance();

  @InjectMocks
  private MovieMapperImpl mapper;

  @Mock
  private ActorMapper actorMapper;

  @Mock
  private DirectorMapper directorMapper;


  @Test
  public void testToModel() {
    var dto = movieDtoTemplate.getValid();
    var model = mapper.toModel(dto);

    Assertions.assertThat(model)
      .hasFieldOrPropertyWithValue("id", dto.getId())
      .hasFieldOrPropertyWithValue("name", dto.getName())
      .hasFieldOrPropertyWithValue("released", dto.getReleased())
      .hasFieldOrPropertyWithValue("censorship", dto.getCensorship())
      .hasFieldOrProperty("director")
      .hasFieldOrProperty("cast");
  }

  @Test
  public void testToDto() {
    var model = movieTemplate.getValid();
    var dto = mapper.toDto(model);

    Assertions.assertThat(dto)
      .hasFieldOrPropertyWithValue("id", model.getId())
      .hasFieldOrPropertyWithValue("name", model.getName())
      .hasFieldOrPropertyWithValue("released", model.getReleased())
      .hasFieldOrPropertyWithValue("censorship", model.getCensorship())
      .hasFieldOrProperty("director")
      .hasFieldOrProperty("cast");
  }

  @Test
  public void testListToDto() {
    int amount = 3;
    var modelList = testUtil.gimme(amount, movieTemplate::getValid);
    var dtoList = mapper.toDto(modelList);

    for (int i = 0; i < dtoList.size(); i++) {
      MovieDto dto = dtoList.get(i);
      Movie model = modelList.get(i);

      Assertions.assertThat(dto)
        .hasFieldOrPropertyWithValue("id", model.getId())
        .hasFieldOrPropertyWithValue("name", model.getName())
        .hasFieldOrPropertyWithValue("released", model.getReleased())
        .hasFieldOrPropertyWithValue("censorship", model.getCensorship())
        .hasFieldOrProperty("director")
        .hasFieldOrProperty("cast");
    }
  }
}
