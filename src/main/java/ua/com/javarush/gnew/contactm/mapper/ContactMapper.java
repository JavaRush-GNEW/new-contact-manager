package ua.com.javarush.gnew.contactm.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.com.javarush.gnew.contactm.DTOs.ContactDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactDTO toDto(Contact contact);
    Contact toEntity(ContactDTO contactDTO);

    @AfterMapping
    default void setParentReferences(@MappingTarget Contact contact) {
        if (contact.getEmails() != null) {
            contact.getEmails().forEach(email -> email.setContact(contact));
        }
        if (contact.getPhones() != null) {
            contact.getPhones().forEach(phone -> phone.setContact(contact));
        }
        if (contact.getNetworks() != null) {
            contact.getNetworks().forEach(network -> network.setContact(contact));
        }
    }
}