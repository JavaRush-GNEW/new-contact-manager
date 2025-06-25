package ua.com.javarush.gnew.contactm.controller.rest;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.javarush.gnew.contactm.DTOs.ContactBookDTO;
import ua.com.javarush.gnew.contactm.entity.ContactBook;
import ua.com.javarush.gnew.contactm.mapper.ContactBookMapper;
import ua.com.javarush.gnew.contactm.repository.ContactBookRepository;

@RestController
@RequestMapping("/api/v1/contact-book")
public class ContactBookControllerApi {

  private final ContactBookRepository contactBookRepository;
  private final ContactBookMapper contactBookMapper;

  public ContactBookControllerApi(
      ContactBookRepository contactBookRepository, ContactBookMapper contactBookMapper) {
    this.contactBookRepository = contactBookRepository;
    this.contactBookMapper = contactBookMapper;
  }

  @GetMapping
  public ResponseEntity<ContactBookDTO> getContactBook(@RequestParam("id") Long id) {
    return contactBookRepository
        .findById(id)
        .map(book -> new ResponseEntity<>(contactBookMapper.toDto(book), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<ContactBookDTO> save(@Valid @RequestBody ContactBookDTO contactBookDTO) {
    ContactBook book = contactBookMapper.toEntity(contactBookDTO);
    ContactBook saved = contactBookRepository.save(book);
    return new ResponseEntity<>(contactBookMapper.toDto(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<ContactBookDTO> update(
      @RequestParam("id") Long id, @RequestBody ContactBookDTO contactBookDTO) {
    Optional<ContactBook> existing = contactBookRepository.findById(id);
    if (existing.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ContactBook bookToUpdate = contactBookMapper.toEntity(contactBookDTO);
    bookToUpdate.setId(id);

    ContactBook saved = contactBookRepository.save(bookToUpdate);
    return new ResponseEntity<>(contactBookMapper.toDto(saved), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
    if (contactBookRepository.existsById(id)) {
      contactBookRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
