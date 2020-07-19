package br.com.fs.api.movies.templates;

import br.com.fs.api.movies.BaseTemplate;
import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.Movie;

import java.util.concurrent.TimeUnit;

public class MovieTemplate extends BaseTemplate {

  protected ActorTemplate actorTemplate = ActorTemplate.getInstance();

  protected DirectorTemplate directorTemplate = DirectorTemplate.getInstance();

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

}
