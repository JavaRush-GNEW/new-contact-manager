package ua.com.javarush.gnew.contactm.mapper;

import org.mapstruct.*;
import ua.com.javarush.gnew.contactm.DTOs.PhoneDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.entity.Phone;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    PhoneDTO toDto(Phone phone);

    @Mapping(target = "contact", ignore = true)
    Phone toEntity(PhoneDTO phoneDTO);

    @AfterMapping
    default void setContact(@MappingTarget Phone phone, @Context Contact contact) {
        phone.setContact(contact);
    }
}
