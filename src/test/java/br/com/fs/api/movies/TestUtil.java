package br.com.fs.api.movies;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TestUtil {

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
