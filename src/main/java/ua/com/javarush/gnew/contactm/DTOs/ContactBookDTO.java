package ua.com.javarush.gnew.contactm.DTOs;

import jakarta.validation.constraints.NotBlank;
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

  @NotBlank(message = "Name must not be blank")
  private String name;

  private List<ContactDTO> contacts;
}
