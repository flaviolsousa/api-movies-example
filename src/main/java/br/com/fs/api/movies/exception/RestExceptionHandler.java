package br.com.fs.api.movies.exception;

import br.com.fs.api.movies.model.error.ErrorResponse;
import br.com.fs.api.movies.model.error.Violation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler(ApiMovieValidationException.class)
  ResponseEntity<ErrorResponse> onApiMovieException(ApiMovieValidationException e) {
    var body = new ErrorResponse(BAD_REQUEST, e);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(ApiMovieException.class)
  ResponseEntity<ErrorResponse> onApiMovieException(ApiMovieException e) {
    var body = new ErrorResponse(INTERNAL_SERVER_ERROR, e);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ResponseEntity<ErrorResponse> onConstraintValidationException(ConstraintViolationException e) {
    var body = new ErrorResponse(BAD_REQUEST, "Constraint validation failed");
    body.setViolations(
      e.getConstraintViolations().stream()
        .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
        .collect(Collectors.toList())
    );
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    var body = new ErrorResponse(BAD_REQUEST, "Argument validation failed");
    body.setViolations(
      e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList())
    );
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler({
    MissingServletRequestParameterException.class,
    MethodArgumentTypeMismatchException.class,
    IllegalArgumentException.class
  })
  ResponseEntity<ErrorResponse> onMissingServletRequestParameterException(Exception e) {
    var body = new ErrorResponse(BAD_REQUEST, e);
    return ResponseEntity.badRequest().body(body);
  }


  @ExceptionHandler({Exception.class})
  ResponseEntity<ErrorResponse> onAnyException(Exception e) {
    log.error("Global Handler", e);
    var body = new ErrorResponse(INTERNAL_SERVER_ERROR, e);
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
  }

}
