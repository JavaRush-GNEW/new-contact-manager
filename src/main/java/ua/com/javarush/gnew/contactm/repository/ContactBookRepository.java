package ua.com.javarush.gnew.contactm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.ContactBook;

public interface ContactBookRepository extends JpaRepository<ContactBook, Long> {
}
