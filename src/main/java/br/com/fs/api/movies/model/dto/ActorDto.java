package br.com.fs.api.movies.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {

  @ApiModelProperty(example = "Margot Robbie")
  @Pattern(regexp = "^\\p{L}[\\p{L} .'-]*$", message = "^\\p{L}[\\p{L} .'-]*$^\\p{L}[\\p{L} .'-]*$")
  @Size(min = 1, max = 100)
  @NotNull
  private String name;

}
