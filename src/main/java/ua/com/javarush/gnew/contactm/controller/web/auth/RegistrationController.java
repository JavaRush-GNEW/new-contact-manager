package ua.com.javarush.gnew.contactm.controller.web.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.services.AppUserService;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
  private final AppUserService appUserService;

  @GetMapping
  public String registration(Model model) {
    model.addAttribute("user", new AppUser());
    return "registration";
  }

  @PostMapping
  public String registerUser(@ModelAttribute AppUser user, Model model) {
    appUserService.register(user);
    return "redirect:/login";
  }
}
