package br.com.fs.api.movies;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class TestUtil {

  @Getter
  private static final TestUtil instance = new TestUtil();

  private Faker faker = new Faker();

  public <T> List<T> gimme(int amount, Supplier<T> templateFunction) {
    Objects.requireNonNull(templateFunction);
    return Stream.generate(templateFunction)
      .limit(amount)
      .collect(Collectors.toList());
  }

  public LocalDate toLocalDate(Date dateToConvert) {
    return LocalDate.ofInstant(
      dateToConvert.toInstant(), ZoneId.systemDefault());
  }

}
