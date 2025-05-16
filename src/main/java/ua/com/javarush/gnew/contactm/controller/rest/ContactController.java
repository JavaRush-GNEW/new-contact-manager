package ua.com.javarush.gnew.contactm.controller.rest;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.entity.Email;
import ua.com.javarush.gnew.contactm.entity.Phone;
import ua.com.javarush.gnew.contactm.entity.SocialNetwork;
import ua.com.javarush.gnew.contactm.repository.ContactRepository;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

  private final ContactRepository contactRepository;

  public ContactController(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  @GetMapping
  public ResponseEntity<Contact> getContact(@RequestParam(value = "id") Long id) {
    Optional<Contact> byId = contactRepository.findById(id);
    return byId.map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<Contact> save(@RequestBody Contact contact) {
    // wire up the owning side of each relationship
    if (contact.getEmails() != null) {
      for (Email e : contact.getEmails()) {
        e.setContact(contact);
      }
    }
    if (contact.getPhones() != null) {
      for (Phone p : contact.getPhones()) {
        p.setContact(contact);
      }
    }
    if (contact.getNetworks() != null) {
      for (SocialNetwork n : contact.getNetworks()) {
        n.setContact(contact);
      }
    }

    Contact saved = contactRepository.save(contact);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }
}
