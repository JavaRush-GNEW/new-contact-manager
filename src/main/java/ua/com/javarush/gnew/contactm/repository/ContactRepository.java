package ua.com.javarush.gnew.contactm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findAllByNameContainingIgnoreCase(String name);
}
