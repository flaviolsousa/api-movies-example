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
  private static final TestUtil instance = TestUtil.newInstance();

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

  public static TestUtil newInstance() {
    return new TestUtil();
  }

  public static <T> T newInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      log.error("Error to create instance of: " + clazz.getName(), e);
      throw new RuntimeException(e);
    }
  }

}
