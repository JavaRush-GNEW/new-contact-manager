package ua.com.javarush.gnew.contactm.DTOs;

import java.util.List;
import lombok.*;
import ua.com.javarush.gnew.contactm.entity.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDTO {
  private long id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String email;
  private String imageUrl;
  private List<UserRole> userRole;
}
