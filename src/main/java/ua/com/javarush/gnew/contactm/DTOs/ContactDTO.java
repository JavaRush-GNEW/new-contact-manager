package ua.com.javarush.gnew.contactm.DTOs;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ContactDTO {
  private long id;
  private String name;
  private String lastName;
  private String imageUrl;
  private List<EmailDTO> emails;
  private List<PhoneDTO> phones;
  private List<SocialNetworkDTO> networks;

  public ContactDTO() {
    this.emails = new ArrayList<>();
    this.phones = new ArrayList<>();
    this.networks = new ArrayList<>();
  }
}
