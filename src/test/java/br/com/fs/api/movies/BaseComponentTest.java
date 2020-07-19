package br.com.fs.api.movies;

import br.com.fs.api.movies.templates.ActorTemplate;
import br.com.fs.api.movies.templates.DirectorTemplate;
import br.com.fs.api.movies.templates.MovieTemplate;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
@Profile("test")
public abstract class BaseComponentTest {

  protected TestUtil util = new TestUtil();

  @Before
  public void init(){
    MockitoAnnotations.initMocks(this);
  }

}
