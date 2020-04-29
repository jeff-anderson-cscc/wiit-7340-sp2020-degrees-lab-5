package edu.cscc.degrees.ui;

import edu.cscc.degrees.services.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebContentController {

  private final ContentService contentService;

  public WebContentController(
    ContentService contentService) {this.contentService = contentService;}


  // @GetMapping("/")
  public String handleIndex() {
    return "index";
  }

  // @GetMapping("/about")


  // @GetMapping("/menu")


}
