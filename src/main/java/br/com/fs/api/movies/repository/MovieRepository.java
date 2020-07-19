package br.com.fs.api.movies.repository;

import br.com.fs.api.movies.model.Censorship;
import br.com.fs.api.movies.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

  List<Movie> findByCensorship(Censorship censorship);

  Optional<Movie> findByNameRegex(String name);

}
