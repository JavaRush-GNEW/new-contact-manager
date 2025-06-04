package ua.com.javarush.gnew.contactm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.entity.UserRole;
import ua.com.javarush.gnew.contactm.repository.AppUserRepository;

@Service
public class CustomUserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {
  @Autowired private AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = appUserRepository.findByUsername(username);
    if (appUser == null) {
      throw new UsernameNotFoundException(username + " not found");
    }

    return User.builder()
        .username(appUser.getUsername())
        .password(appUser.getPassword())
        .roles(UserRole.ADMIN.name()) // @TODO Implement role in the DB
        .build();
  }
}
