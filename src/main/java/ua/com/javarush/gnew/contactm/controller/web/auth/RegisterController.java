package ua.com.javarush.gnew.contactm.controller.web.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.services.AppUserService;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
  private final AppUserService appUserService;
  private final PasswordEncoder passwordEncoder;
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @GetMapping
  public String registration(Model model) {
    model.addAttribute("user", new AppUserDTO());
    return "register";
  }

  @PostMapping
  public String registerUser(@ModelAttribute AppUserDTO user, Model model) {

    AppUser existingAppUserUsername = appUserService.findByUserName(user.getUsername());
    if (existingAppUserUsername != null && existingAppUserUsername.getUsername() != null) {
      return "redirect:/register?fail";
    }
    
    appUserService.register(user);
    
    return "redirect:/?success";
  }
}
