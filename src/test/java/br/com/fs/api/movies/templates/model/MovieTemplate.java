package br.com.fs.api.movies.templates.model;

import br.com.fs.api.movies.templates.BaseTemplate;
import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieTemplate extends BaseTemplate {

  @Getter
  private static final MovieTemplate instance = new MovieTemplate();

  protected final ActorTemplate actorTemplate = ActorTemplate.getInstance();

  protected final DirectorTemplate directorTemplate = DirectorTemplate.getInstance();

  public Movie getValid() {
    return Movie.builder()
      .id(faker.regexify("[a-z1-9]{10}"))
      .name(faker.book().title())
      .released(util.toLocalDate(faker.date().past(20 * 365, TimeUnit.DAYS)))
      .censorship(faker.options().option(Censorship.values()))
      .director(directorTemplate.getValid())
      .cast(util.gimme(5, actorTemplate::getValid))
      .build();
  }

  public Movie getNew() {
    final var movie = this.getValid();
    movie.setId(null);
    return movie;
  }

}
