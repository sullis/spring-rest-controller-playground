package com.example.boot3;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
  @PostMapping(path = "/echo_plain_text",
      consumes = "text/plain",
      produces = "text/plain")
  public String echo_plain_text(@RequestBody String input) {
    return input;
  }

  @PostMapping(path = "/echo_json",
      consumes = "application/json",
      produces = "application/json")
  public String echo_json(@RequestBody String input) {
    return input;
  }
}
