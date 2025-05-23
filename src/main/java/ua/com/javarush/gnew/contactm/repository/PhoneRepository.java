package ua.com.javarush.gnew.contactm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
