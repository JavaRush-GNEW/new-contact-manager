package ua.com.javarush.gnew.contactm.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping(value = "/api/test", produces = "application/json")
  public String securedHello() {
    return "Доступ дозволено! JWT працює правильно.";
  }
}
