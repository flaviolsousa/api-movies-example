package br.com.fs.api.movies.exception;

import br.com.fs.api.movies.model.error.ErrorResponse;
import br.com.fs.api.movies.model.error.Violation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ResponseEntity<ErrorResponse> onConstraintValidationException(ConstraintViolationException e) {
    var body = new ErrorResponse(HttpStatus.BAD_REQUEST, e);
    body.setViolations(
      e.getConstraintViolations().stream()
        .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
        .collect(Collectors.toList())
    );
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    var body = new ErrorResponse(HttpStatus.BAD_REQUEST, e);
    body.setViolations(
      e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList())
    );
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<ErrorResponse> onAnyException(Exception e) {
    log.error("Global Handler", e);
    var body = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

}
