package ua.com.javarush.gnew.contactm.controller.web.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

//  @GetMapping("/login")
//  public String getLoginPage() {
//    return "login";
//  }
  
  @GetMapping("/login")
  public String getLoginPage() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated() &&
            !(auth instanceof AnonymousAuthenticationToken)) {
      return "redirect:/?continue";
    }
    return "login";
  }
}
