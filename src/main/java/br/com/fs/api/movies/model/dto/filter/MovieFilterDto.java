package br.com.fs.api.movies.model.dto.filter;

import br.com.fs.api.movies.model.Censorship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieFilterDto {

  private Censorship censorship;

}
