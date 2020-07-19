package br.com.fs.api.movies.service;

import br.com.fs.api.movies.BaseComponentTest;
import br.com.fs.api.movies.templates.MovieTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.InjectMocks;

@Slf4j
public class MovieServiceTest extends BaseComponentTest {

  @InjectMocks
  private MovieTemplate movieTemplate;

  @Test
  public void saveWithSuccess() {
    log.info(movieTemplate.getValid().toString());
  }

}
