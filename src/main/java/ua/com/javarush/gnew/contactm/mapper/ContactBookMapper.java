package ua.com.javarush.gnew.contactm.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.com.javarush.gnew.contactm.DTOs.ContactBookDTO;
import ua.com.javarush.gnew.contactm.entity.ContactBook;

@Mapper(
    componentModel = "spring",
    uses = {ContactMapper.class})
public interface ContactBookMapper {

  ContactBookDTO toDto(ContactBook contactBook);

  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  ContactBook toEntity(ContactBookDTO contactBookDTO);

  @AfterMapping
  default void setContacts(@MappingTarget ContactBook contactBook) {
    if (contactBook.getContacts() != null) {
      contactBook.getContacts().forEach(contact -> contact.setContactBook(contactBook));
    }
  }
}
