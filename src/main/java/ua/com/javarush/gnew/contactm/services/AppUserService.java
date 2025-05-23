package ua.com.javarush.gnew.contactm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.repository.AppUserRepository;

@Service
@RequiredArgsConstructor
public class AppUserService {
  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;


  public void register(AppUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    appUserRepository.save(user);
  }

}
