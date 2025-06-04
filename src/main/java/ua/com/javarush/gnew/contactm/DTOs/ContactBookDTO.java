package ua.com.javarush.gnew.contactm.DTOs;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactBookDTO {
  private long id;
  private List<ContactDTO> contacts;
}
