package br.com.fs.api.movies.templates;

import br.com.fs.api.movies.TestUtil;
import com.github.javafaker.Faker;

public abstract class BaseTemplate {

  protected final Faker faker = new Faker();

  protected final TestUtil util = TestUtil.getInstance();

}
