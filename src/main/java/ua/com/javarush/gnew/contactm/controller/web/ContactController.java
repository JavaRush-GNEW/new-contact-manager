package ua.com.javarush.gnew.contactm.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.mapper.ContactMapper;
import ua.com.javarush.gnew.contactm.services.ContactService;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

  private final ContactService contactService;
  private final ContactMapper contactMapper;

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    ContactDTO dto = contactMapper.toDto(contactService.findById(id));
    model.addAttribute("contact", dto);
    return "contact/edit";
  }

  @PostMapping("/edit")
  public String save(@ModelAttribute ContactDTO contactDTO) {
    contactService.save(contactDTO);
    return "redirect:/";
  }

  @PostMapping("/remove/{id}")
  public String remove(@PathVariable Long id) {
    contactService.delete(id);
    return "redirect:/";
  }
}
