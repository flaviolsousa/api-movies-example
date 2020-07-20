package br.com.fs.api.movies.templates.dto;

import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.dto.MovieDto;
import br.com.fs.api.movies.templates.BaseTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieDtoTemplate extends BaseTemplate {

  @Getter
  private static final MovieDtoTemplate instance = new MovieDtoTemplate();

  protected final ActorDtoTemplate actorDtoTemplate = ActorDtoTemplate.getInstance();

  protected final DirectorDtoTemplate directorDtoTemplate = DirectorDtoTemplate.getInstance();

  public MovieDto getValid() {
    return MovieDto.builder()
      .id(faker.regexify("[a-z1-9]{10}"))
      .name(faker.book().title() + faker.regexify(" [1-9]{3}"))
      .released(util.toLocalDate(faker.date().past(20 * 365, TimeUnit.DAYS)))
      .censorship(faker.options().option(Censorship.values()))
      .director(directorDtoTemplate.getValid())
      .cast(util.gimme(5, actorDtoTemplate::getValid))
      .build();
  }

  public MovieDto getNew() {
    var dto = this.getValid();
    dto.setId(null);
    return dto;
  }

  public MovieDto getNewCensored() {
    var dto = this.getValid();
    dto.setId(null);
    dto.setCensorship(Censorship.CENSORED);
    return dto;
  }

  public MovieDto getNewUncensored() {
    var dto = this.getValid();
    dto.setId(null);
    dto.setCensorship(Censorship.UNCENSORED);
    return dto;
  }

  public MovieDto getNewFixedName() {
    var dto = this.getNew();
    dto.setName("Flávio L. O'Müçer");
    return dto;
  }

  public MovieDto getWithoutLists() {
    var dto = this.getValid();
    dto.setCast(null);
    dto.setDirector(null);
    return dto;
  }

}
