package ua.com.javarush.gnew.contactm.mapper;

import org.mapstruct.*;
import ua.com.javarush.gnew.contactm.DTOs.SocialNetworkDTO;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.entity.SocialNetwork;

@Mapper(componentModel = "spring")
public interface SocialNetworkMapper {

    SocialNetworkDTO toDto(SocialNetwork network);

    @Mapping(target = "contact", ignore = true)
    SocialNetwork toEntity(SocialNetworkDTO dto);

    @AfterMapping
    default void setContact(@MappingTarget SocialNetwork network, @Context Contact contact) {
        network.setContact(contact);
    }
}
