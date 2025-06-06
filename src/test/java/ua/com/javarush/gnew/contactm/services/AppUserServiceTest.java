package ua.com.javarush.gnew.contactm.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.repository.AppUserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

  @Mock
  private AppUserRepository appUserRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AppUserService appUserService;


  @Test
  void register_ShouldEncodePasswordAndSaveUser() {
    // Arrange
    String username = "username";
    String password = "password";

    String encodedPassword = "encodedPassword";

    AppUserDTO appUserDTO = AppUserDTO.builder()
            .username(username)
            .password(password)
            .build();

    when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

    ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);

    //Act
    appUserService.register(appUserDTO);

    //Assert
    verify(passwordEncoder, times(1)).encode(password);


    verify(appUserRepository, times(1)).save(appUserArgumentCaptor.capture());

    AppUser savedAppUser = appUserArgumentCaptor.getValue();

    assertEquals(username, savedAppUser.getUsername());
    assertEquals(encodedPassword, savedAppUser.getPassword());
  }

  @Test
  void findByUserName() {
  }
}