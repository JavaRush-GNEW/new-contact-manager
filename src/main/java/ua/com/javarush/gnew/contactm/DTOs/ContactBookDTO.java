package ua.com.javarush.gnew.contactm.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContactBookDTO {
    private long id;
    private List<ContactDTO> contacts;
}
