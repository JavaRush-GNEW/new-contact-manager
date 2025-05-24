package ua.com.javarush.gnew.contactm.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {
    private long id;
    private String name;
    private List<EmailDTO> emails;
    private List<PhoneDTO> phones;
    private List<SocialNetworkDTO> networks;
}
