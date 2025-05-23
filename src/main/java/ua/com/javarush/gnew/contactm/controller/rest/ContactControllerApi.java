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
public class ContactControllerApi {

  private final ContactRepository contactRepository;

  public ContactControllerApi(ContactRepository contactRepository) {
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

  @PutMapping
  public ResponseEntity<Contact> update(@RequestParam(value = "id") Long id, @RequestBody Contact updatedContact) {
    Optional<Contact> existingOpt = contactRepository.findById(id);

    if (existingOpt.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Contact existing = existingOpt.get();

    existing.setName(updatedContact.getName());
    existing.setEmails(updatedContact.getEmails());
    existing.setPhones(updatedContact.getPhones());
    existing.setNetworks(updatedContact.getNetworks());

    if (existing.getEmails() != null) {
      for (Email e : existing.getEmails()) {
        e.setContact(existing);
      }
    }
    if (existing.getPhones() != null) {
      for (Phone p : existing.getPhones()) {
        p.setContact(existing);
      }
    }
    if (existing.getNetworks() != null) {
      for (SocialNetwork n : existing.getNetworks()) {
        n.setContact(existing);
      }
    }

    Contact saved = contactRepository.save(existing);
    return new ResponseEntity<>(saved, HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestParam(value = "id") Long id) {
    Optional<Contact> contactOpt = contactRepository.findById(id);
    if (contactOpt.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    contactRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
