package ua.com.javarush.gnew.contactm.controller.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.services.ContactService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

  private final ContactService contactService;

  @GetMapping
  public String home(Model model) {
    List<Contact> all = contactService.findAll();
    model.addAttribute("tableName", "All contacts");
    model.addAttribute("contacts", all);
    return "home";
  }
}
