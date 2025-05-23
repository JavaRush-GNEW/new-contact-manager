package ua.com.javarush.gnew.contactm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Integer> {
}
