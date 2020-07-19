package br.com.fs.api.movies.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {

  @ApiModelProperty(example = "Margot Robbie")
  @Pattern(regexp = "^\\p{L}[\\p{L} .'-]{1,30}$")
  private String name;

}
