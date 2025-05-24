package ua.com.javarush.gnew.contactm.mapper;

import org.mapstruct.*;
import ua.com.javarush.gnew.contactm.DTOs.EmailDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.entity.Email;

@Mapper(componentModel = "spring")
public interface EmailMapper {

  EmailDTO toDto(Email email);

  @Mapping(target = "contact", ignore = true)
  Email toEntity(EmailDTO emailDTO);

  @AfterMapping
  default void setContact(@MappingTarget Email email, @Context Contact contact) {
    email.setContact(contact);
  }
}