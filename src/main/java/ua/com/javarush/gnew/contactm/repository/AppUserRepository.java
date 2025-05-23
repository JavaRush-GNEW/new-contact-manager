package ua.com.javarush.gnew.contactm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.AppUser;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  List<AppUser> findByUserName(String userName);
}
