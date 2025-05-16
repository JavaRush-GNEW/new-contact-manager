package ua.com.javarush.gnew.contactm.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.repository.ContactRepository;

import java.util.List;

@Controller
public class HomeController {

  private final ContactRepository contactRepository;

  public HomeController(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  @GetMapping
  public String home(Model model) {
    List<Contact> all = contactRepository.findAll();
    model.addAttribute("tableName", "All contacts");
    model.addAttribute("contacts", all);
    return "home";
  }

}
