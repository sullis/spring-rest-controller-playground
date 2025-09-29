package com.example.boot2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
  @PostMapping(path = "/consumes_text_plain",
      consumes = "text/plain",
      produces = "text/plain")
  public String consumes_text_plain(@RequestBody String input) {
    return input;
  }

  @PostMapping(path = "/consumes_application_json",
      consumes = "application/json",
      produces = "application/json")
  public String consumes_application_json(@RequestBody String input) {
    return input;
  }

  @PostMapping(path = "/consumes_anything")
  public String consumes_anything(@RequestBody String input) {
    return input;
  }
}
