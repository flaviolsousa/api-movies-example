package br.com.fs.api.movies.model.error;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
public class Violation {

  private final String fieldName;
  private final String message;

}
