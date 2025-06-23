package ua.com.javarush.gnew.contactm.controller.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.services.ContactService;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final ContactService contactService;

  @GetMapping
  public String home(Model model) {
    AppUser appUser = new AppUser();
    appUser.setUsername("testUser");
    appUser.setFirstName("Test");
    appUser.setLastName("User");
    appUser.setEmail("<EMAIL>");

    model.addAttribute("appUser", appUser);

    return "dashboard/dashboard";
  }

  @GetMapping(value = "/contact/list")
  public String contactList(Model model) {
    AppUser appUser = new AppUser();
    appUser.setUsername("testUser");
    appUser.setFirstName("Test");
    appUser.setLastName("User");
    appUser.setEmail("<EMAIL>");
    model.addAttribute("appUser", appUser);

    List<Contact> all = contactService.findAll();
    model.addAttribute("tableName", "All contacts");
    model.addAttribute("contacts", all);

    return "dashboard/contact-list";
  }
}
