package ua.com.javarush.gnew.contactm.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.mapper.ContactMapper;
import ua.com.javarush.gnew.contactm.repository.ContactRepository;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "contacts") // all methods will use the "contacts" cache by default
public class ContactService {

  private final ContactRepository contactRepository;
  private final ContactMapper contactMapper;

  /** Save a Contact entity and update the cache entry for it. */
  @CachePut(key = "#contact.id")
  public Contact save(Contact contact) {
    return contactRepository.save(contact);
  }

  /** Save via DTO and update the cache entry for the resulting Contact. */
  @CachePut(key = "#result.id")
  public Contact save(ContactDTO contactDto) {
    Contact contact = contactMapper.toEntity(contactDto);
    return contactRepository.save(contact);
  }

  /**
   * Read-through cache: will return from cache if present, otherwise load from DB and cache it.
   * Returns Optional<Contact> for better null handling.
   */
  @Cacheable(key = "#id")
  public Optional<Contact> findById(Long id) {
    return contactRepository.findById(id);
  }

  /** Check if a contact exists by ID. */
  @Cacheable(key = "'exists-' + #id")
  public boolean existsById(Long id) {
    return contactRepository.existsById(id);
  }

  /** Delete from the DB and evict the cache entry. */
  @CacheEvict(key = "#id")
  public void delete(Long id) {
    contactRepository.deleteById(id);
  }

  /** Delete by ID from the DB and evict the cache entry. */
  @CacheEvict(key = "#id")
  public void deleteById(Long id) {
    contactRepository.deleteById(id);
  }

  @Cacheable(key = "'all'")
  public List<Contact> findAll() {
    return contactRepository.findAll();
  }

  // find all by name
  @Cacheable(key = "#name")
  public Iterable<Contact> findAllByName(String name) {
    return contactRepository.findAllByNameContainingIgnoreCase(name);
  }
}
