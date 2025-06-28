package ua.com.javarush.gnew.contactm.services;

import java.util.Arrays;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.entity.UserRole;
import ua.com.javarush.gnew.contactm.repository.AppUserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {
  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;

  public void register(AppUserDTO appUserDto) {
    AppUser appUser = new AppUser();
    appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
    appUser.setUsername(appUserDto.getUsername());
    // appUser.setUserRole(new HashSet(Arrays.asList(UserRole.USER, UserRole.ADMIN)));
    appUser.setUserRole(new HashSet(Arrays.asList(UserRole.USER)));
    // appUser.setUserRole(new HashSet(Arrays.asList(UserRole.ADMIN)));
    appUserRepository.save(appUser);
  }

  public boolean existsByUsername(String userName) {
    return appUserRepository.existsByUsername(userName);
  }

  public boolean existUsernamePassword(AppUserDTO appUserDto) {
    AppUser appUser = findByUserName(appUserDto.getUsername());
    return passwordEncoder.matches(appUserDto.getPassword(), appUser.getPassword());
  }

  public AppUser findByUserName(String userName) {
    return appUserRepository.findByUsername(userName);
  }
}
