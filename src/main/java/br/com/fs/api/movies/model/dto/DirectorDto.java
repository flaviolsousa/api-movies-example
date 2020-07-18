package br.com.fs.api.movies.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {

  @ApiModelProperty(example = "Quentin Tarantino")
  @NotBlank
  private String name;

}
