package ua.com.javarush.gnew.contactm.controller.web.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("")
public class LoginController {

  @GetMapping("/login")
  public String getLoginPage() {
    return "login";
  }

}
