package br.com.fs.api.movies.config;

import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This component has like objective of ensuring that there are
 * no doubts or problems on start and debug of application by developers
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CheckDependenciesConfig {

  private final MongoTemplate mongoTemplate;

  @PostConstruct
  public void init() {
    var ping = new BasicDBObject("ping", "1");
    try {
      mongoTemplate.getDb().runCommand(ping);
    } catch (Exception e) {
      showInstructionMessage();
    }
  }

  private void showInstructionMessage() {
    log.error("\n\n#############################\n\n" +
      "Container of MongoDB NotFound\n" +
      "-----------------------------\n" +
      "Execute before run the application:\n" +
      "  make run-dependencies\n\n" +
      "Start stack with docker-compose:\n" +
      "  make run\n\n" +
      "#############################\n\n");
  }

}
