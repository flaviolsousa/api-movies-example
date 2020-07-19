package br.com.fs.api.movies.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {

  @ApiModelProperty(example = "Quentin Tarantino")
  @Pattern(regexp = "^\\p{L}[\\p{L} .'-]{1,30}")
  @NotNull
  private String name;

}
