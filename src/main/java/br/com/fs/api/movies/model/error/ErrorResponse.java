package br.com.fs.api.movies.model.error;

import br.com.fs.api.movies.exception.ApiMovieException;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

  @ApiModelProperty(value = "Moment the exception occurred", required = true)
  private LocalDateTime timestamp;

  @ApiModelProperty(value = "The error type", required = true)
  private String error;

  @ApiModelProperty(value = "The error message", required = true)
  private String message;

  @ApiModelProperty(value = "Describes a constraint violation", required = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Violation> violations;

  ErrorResponse(String error, String message) {
    this.timestamp = LocalDateTime.now();
    this.error = error;
    this.message = message;
  }

  ErrorResponse(String error, ApiMovieException e) {
    this(error, e.getMessage());
    this.setViolations(e.getViolations());
  }

  ErrorResponse(String error, Exception e) {
    this(error, e.getMessage());
  }

  public ErrorResponse(HttpStatus httpStatus, Exception e) {
    this(httpStatus.toString(), e);
  }

}
