package br.com.fs.api.movies.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

  @Id
  private String id;

  @Indexed(name = "name_", unique = true)
  @NotBlank
  private String name;

  @NotNull
  private LocalDate released;

  @Indexed(name = "censorship_")
  @NotNull
  private Censorship censorship;

  @NotBlank
  private Director director;

  @NotNull
  @NotEmpty
  @Size(max = 10)
  private List<Actor> cast;

}
