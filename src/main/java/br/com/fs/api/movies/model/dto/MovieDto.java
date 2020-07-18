package br.com.fs.api.movies.model.dto;

import br.com.fs.api.movies.model.dto.ActorDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

  @NotBlank
  private String name;

  @ApiModelProperty(example = "2020-01-15")
  @NotNull
  private LocalDate released;

  @ApiModelProperty(allowableValues = "CENSURED,NOT_CENSURED")
  @NotNull
  private Boolean censorship;

  @NotBlank
  private String director;

  @NotNull
  @NotEmpty
  @Size(max = 10)
  private List<ActorDto> cast;

}
