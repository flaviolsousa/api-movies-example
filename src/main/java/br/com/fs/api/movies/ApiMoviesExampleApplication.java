package br.com.fs.api.movies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ApiMoviesExampleApplication {

  public static void main(String[] args) {
    var ctx = SpringApplication.run(ApiMoviesExampleApplication.class, args);

    var env = ctx.getEnvironment();
    var serverPort = env.getProperty("local.server.port");
    log.info("\n###\n\n"
      + "http://localhost:"
      + serverPort
      + "/\n\n###"
    );
  }

}
