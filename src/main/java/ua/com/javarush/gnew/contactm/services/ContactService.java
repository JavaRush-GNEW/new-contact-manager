package ua.com.javarush.gnew.contactm.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.mapper.ContactMapper;
import ua.com.javarush.gnew.contactm.repository.ContactRepository;

@Service
@RequiredArgsConstructor
public class ContactService {

  private final ContactRepository contactRepository;
  private final ContactMapper contactMapper;

  public void save(Contact contact) {
    contactRepository.save(contact);
  }

  public void save(ContactDTO contact) {
    contactRepository.save(contactMapper.toEntity(contact));
  }

  public Contact findById(Long id) {
    return contactRepository.findById(id).orElse(null);
  }

  public void delete(Long id) {
    contactRepository.deleteById(id);
  }

  public List<Contact> findAll() {
    return contactRepository.findAll();
  }
}
