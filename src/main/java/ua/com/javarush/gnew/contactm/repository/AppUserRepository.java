package ua.com.javarush.gnew.contactm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.javarush.gnew.contactm.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  AppUser findByUsername(String userName);

  AppUser findByEmail(String email);

  // Optional<AppUser> findByUserName(String username);
  Boolean existsByUsername(String username);
}
