package ua.com.javarush.gnew.contactm.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.mapper.ContactMapper;
import ua.com.javarush.gnew.contactm.repository.ContactRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactControllerApi {

  private final ContactRepository contactRepository;
  private final ContactMapper contactMapper;

  public ContactControllerApi(ContactRepository contactRepository, ContactMapper contactMapper) {
    this.contactRepository = contactRepository;
    this.contactMapper = contactMapper;
  }

  @GetMapping
  public ResponseEntity<ContactDTO> getContact(@RequestParam("id") Long id) {
    return contactRepository.findById(id)
            .map(contact -> new ResponseEntity<>(contactMapper.toDto(contact), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<ContactDTO> save(@RequestBody ContactDTO contactDTO) {
    Contact contact = contactMapper.toEntity(contactDTO);
    Contact saved = contactRepository.save(contact);
    return new ResponseEntity<>(contactMapper.toDto(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<ContactDTO> update(@RequestParam("id") Long id, @RequestBody ContactDTO contactDTO) {
    Optional<Contact> existingOpt = contactRepository.findById(id);
    if (existingOpt.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Contact contactToUpdate = contactMapper.toEntity(contactDTO);
    contactToUpdate.setId(id);

    Contact saved = contactRepository.save(contactToUpdate);
    return new ResponseEntity<>(contactMapper.toDto(saved), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
    if (contactRepository.existsById(id)) {
      contactRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
