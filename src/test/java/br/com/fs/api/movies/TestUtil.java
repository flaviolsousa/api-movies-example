package br.com.fs.api.movies;

import br.com.fs.api.movies.model.dto.MovieDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private ObjectMapper objectMapper = new ObjectMapper();

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

  public String toJson(Object objectToJson) throws JsonProcessingException {
    return objectMapper.writeValueAsString(objectToJson);
  }

  public <T> T toObject(String json, Class<T> clazz) throws JsonProcessingException {
    return (T) objectMapper.readValue(json, clazz);
  }
}
