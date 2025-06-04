package ua.com.javarush.gnew.contactm.DTOs;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {
  private long id;
  private String name;
  private String lastName;
  private List<EmailDTO> emails;
  private List<PhoneDTO> phones;
  private List<SocialNetworkDTO> networks;
}
