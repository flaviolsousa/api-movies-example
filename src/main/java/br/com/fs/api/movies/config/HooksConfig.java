package br.com.fs.api.movies.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class HooksConfig {

  private final Environment environment;

  @Value("${spring.application.name}")
  private String applicationName;

  private static final List<String> localProfiles = List.of("local", "test", "all");

  @PostConstruct
  public void init() {
    try {
      var profiles = Arrays.asList(environment.getActiveProfiles());
      log.debug("Profiles: %s", String.join(", ", profiles));
      if (profiles.stream().anyMatch(localProfiles::contains)) {
        configHooks();
      }
    } catch (Exception e) {
      log.warn("Exception on HooksConfig.init", e);
    }
  }

  private void configHooks() throws IOException {
    var projectPath = getProjectPath();
    var hooksPath = getHooksPath(projectPath);

    if (shouldUpdate(hooksPath, projectPath)) {
      log.info("NOT founded 'pre-commit'");
      var source = Path.of(projectPath.toString(), "data", "git", "pre-commit");
      Files.copy(source, hooksPath.resolve("pre-commit"), StandardCopyOption.REPLACE_EXISTING);
    }
  }

  private boolean shouldUpdate(Path hooksPath, Path projectPath) {
    if (hooksPath == null) return false;

    final var preCommitHook = hooksPath.resolve("pre-commit").toFile();
    final var preCommitProject = Path.of(projectPath.toString(), "data", "pre-commit").toFile();

    if (!preCommitHook.exists()) return true;
    if (preCommitHook.length() != preCommitProject.length()) return true;

    return false;
  }

  private Path getHooksPath(Path projectPath) {
    var hooksPath = Path.of(projectPath.toString(), ".git", "hooks");
    if (Files.exists(hooksPath)) return hooksPath;

    hooksPath = projectPath.resolve(Path.of(projectPath.toString(), "..", ".git", "modules", applicationName, "hooks"));
    if (Files.exists(hooksPath)) return hooksPath;

    return null;
  }

  private Path getProjectPath() {
    return Path.of(".").toAbsolutePath();
  }

}
