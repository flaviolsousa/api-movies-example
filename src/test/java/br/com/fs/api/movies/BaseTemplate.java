package br.com.fs.api.movies;

import com.github.javafaker.Faker;
import org.mockito.InjectMocks;

public abstract class BaseTemplate {

  protected Faker faker = new Faker();

  protected TestUtil util = new TestUtil();

}
