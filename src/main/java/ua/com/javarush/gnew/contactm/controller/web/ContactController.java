package ua.com.javarush.gnew.contactm.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.repository.ContactRepository;

import java.util.Optional;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

  private final ContactRepository contactRepository;

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    Optional<Contact> byId = contactRepository.findById(id);
    if (byId.isPresent()) {
      Contact contact = byId.get();
      model.addAttribute("contact", contact);
    }

    return "contact/edit";
  }

  @PostMapping("/edit")
  public String save(@ModelAttribute Contact contact) {
    contactRepository.save(contact);
    return "redirect:/";
  }

}
