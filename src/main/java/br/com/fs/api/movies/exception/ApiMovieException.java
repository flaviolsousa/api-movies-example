package br.com.fs.api.movies.exception;

import br.com.fs.api.movies.model.error.Violation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ApiMovieException extends RuntimeException {

  @Getter
  @Setter
  private List<Violation> violations;

  public ApiMovieException(String message, Throwable cause, List<Violation> violations) {
    super(message, cause);
    this.violations = violations;
  }

}
