package ua.com.javarush.gnew.contactm.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PhoneDTO {
  private long id;
  private String label;
  private String phone;
}
