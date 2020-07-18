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
public class ActorDto {

  @ApiModelProperty(example = "Margot Robbie")
  @NotBlank
  private String name;

}
