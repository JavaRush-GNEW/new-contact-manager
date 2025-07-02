package ua.com.javarush.gnew.contactm.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.javarush.gnew.contactm.services.TestService;

@RestController
@RequestMapping("/api/test")
public class TestController {

  private final TestService testService;

  public TestController(TestService testService) {
    this.testService = testService;
  }

  @GetMapping(produces = "application/json")
  public String securedHello() {
    return "Доступ дозволено!";
  }

  @GetMapping("/simulate-error")
  public void simulateError() {
    testService.throwTestException();
  }
}
