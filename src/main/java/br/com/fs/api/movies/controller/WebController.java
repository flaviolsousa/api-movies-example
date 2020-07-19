package br.com.fs.api.movies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ApiIgnore
@Controller
public class WebController {

  @GetMapping(value = {"", "/", "index", "index.html"})
  public void root(HttpServletResponse response) throws IOException {
    response.sendRedirect("/swagger-ui.html");
  }

}
