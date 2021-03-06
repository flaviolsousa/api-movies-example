package br.com.fs.api.movies.mapper;

import br.com.fs.api.movies.TestUtil;
import br.com.fs.api.movies.model.Movie;
import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.model.dto.filter.MovieFilterDto;
import br.com.fs.api.movies.model.mapper.ActorMapper;
import br.com.fs.api.movies.model.mapper.DirectorMapper;
import br.com.fs.api.movies.model.mapper.MovieMapperImpl;
import br.com.fs.api.movies.templates.dto.MovieDtoTemplate;
import br.com.fs.api.movies.templates.dto.filters.MovieFilterDtoTemplate;
import br.com.fs.api.movies.templates.model.MovieTemplate;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MovieMapperTest {

  private final MovieDtoTemplate movieDtoTemplate = MovieDtoTemplate.getInstance();
  private final MovieFilterDtoTemplate movieFilterDtoTemplate = MovieFilterDtoTemplate.getInstance();
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
    MovieDto movie = null;
    Assertions.assertThat(mapper.toModel(movie)).isNull();
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
  public void testFilterToModel() {
    MovieFilterDto filterDto = null;
    Assertions.assertThat(mapper.toModel(filterDto)).isNull();


    filterDto = movieFilterDtoTemplate.getValid();
    var model = mapper.toModel(filterDto);

    Assertions.assertThat(model)
      .hasFieldOrPropertyWithValue("censorship", filterDto.getCensorship());
  }

  @Test
  public void testToModelNullLists() {
    var dto = movieDtoTemplate.getWithoutLists();
    var model = mapper.toModel(dto);

    Assertions.assertThat(model.getId()).isNotNull();
    Assertions.assertThat(model.getCast()).isNull();
    Assertions.assertThat(model.getDirector()).isNull();
  }

  @Test
  public void testToDto() {
    Movie model = null;
    Assertions.assertThat(mapper.toDto(model)).isNull();

    model = movieTemplate.getValid();
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
    List<Movie> modelList = null;
    Assertions.assertThat(mapper.toDto(modelList)).isNull();

    int amount = 3;
    modelList = testUtil.gimme(amount, movieTemplate::getValid);
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

  @Test
  public void testToDtoNullLists() {
    var model = movieTemplate.getWithoutLists();
    var dto = mapper.toDto(model);

    Assertions.assertThat(dto.getId()).isNotNull();
    Assertions.assertThat(dto.getCast()).isNull();
    Assertions.assertThat(dto.getDirector()).isNull();
  }

}
