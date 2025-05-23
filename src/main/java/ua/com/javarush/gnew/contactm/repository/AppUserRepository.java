package ua.com.javarush.gnew.contactm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
