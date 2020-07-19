package br.com.fs.api.movies.exception;

import br.com.fs.api.movies.model.error.ErrorResponse;
import br.com.fs.api.movies.model.error.Violation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {


  @ExceptionHandler(ApiMovieException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ResponseBody
  ResponseEntity<ErrorResponse> onApiMovieException(ApiMovieException e) {
    var body = new ErrorResponse(INTERNAL_SERVER_ERROR, e);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  ResponseEntity<ErrorResponse> onConstraintValidationException(ConstraintViolationException e) {
    var body = new ErrorResponse(BAD_REQUEST, e);
    body.setViolations(
      e.getConstraintViolations().stream()
        .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
        .collect(Collectors.toList())
    );
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    var body = new ErrorResponse(BAD_REQUEST, e);
    body.setViolations(
      e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList())
    );
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<ErrorResponse> onAnyException(Exception e) {
    log.error("Global Handler", e);
    var body = new ErrorResponse(INTERNAL_SERVER_ERROR, e);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
  }

}
