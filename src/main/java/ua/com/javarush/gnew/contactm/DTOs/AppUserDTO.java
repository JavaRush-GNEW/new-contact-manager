package ua.com.javarush.gnew.contactm.DTOs;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.javarush.gnew.contactm.entity.UserRole;

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
  private List<UserRole> userRole;
}
