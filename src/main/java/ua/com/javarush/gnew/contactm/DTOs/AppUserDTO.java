package ua.com.javarush.gnew.contactm.DTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDTO {
  private long id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String email;
}
