package ua.com.javarush.gnew.contactm.DTOs;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ContactBookDTO {
  private long id;
  private String name;
  private List<ContactDTO> contacts;
}
