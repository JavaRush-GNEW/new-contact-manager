package ua.com.javarush.gnew.contactm.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.mapper.ContactMapper;
import ua.com.javarush.gnew.contactm.services.CloudinaryService;
import ua.com.javarush.gnew.contactm.services.ContactService;

import java.io.IOException;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

  private final ContactService contactService;
  private final ContactMapper contactMapper;
  private final CloudinaryService imageService;

  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    ContactDTO dto = contactMapper.toDto(contactService.findById(id));
    model.addAttribute("contact", dto);
    return "contact/edit";
  }

  @PostMapping("/edit")
  public String editContact(@ModelAttribute ContactDTO contactDTO, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, RedirectAttributes redirectAttributes) {

    try {
      // Handle image upload if provided
      if (imageFile != null && !imageFile.isEmpty()) {
        String imageUrl = imageService.upload(imageFile);
        contactDTO.setImageUrl(imageUrl);
      }

      // Save the contact
      contactService.save(contactDTO);
      redirectAttributes.addFlashAttribute("success", "Contact updated successfully!");

    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Failed to update contact: " + e.getMessage());
    }

    return "redirect:/";
  }


  @PostMapping("/remove/{id}")
  public String remove(@PathVariable Long id) {
    contactService.delete(id);
    return "redirect:/";
  }
}
