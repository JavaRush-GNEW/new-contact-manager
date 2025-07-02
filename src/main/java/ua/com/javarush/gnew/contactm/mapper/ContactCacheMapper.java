package ua.com.javarush.gnew.contactm.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.com.javarush.gnew.contactm.DTOs.ContactCacheDto;
import ua.com.javarush.gnew.contactm.entity.Contact;
import ua.com.javarush.gnew.contactm.entity.Email;
import ua.com.javarush.gnew.contactm.entity.Phone;
import ua.com.javarush.gnew.contactm.entity.SocialNetwork;

@Mapper(componentModel = "spring")
public interface ContactCacheMapper {

  @Mapping(target = "emailAddresses", source = "emails", qualifiedByName = "emailsToStrings")
  @Mapping(target = "phoneNumbers", source = "phones", qualifiedByName = "phonesToStrings")
  @Mapping(target = "socialNetworks", source = "networks", qualifiedByName = "networksToStrings")
  @Mapping(target = "contactBookName", source = "contactBook.name")
  ContactCacheDto toDto(Contact contact);

  List<ContactCacheDto> toDtoList(List<Contact> contacts);

  @Named("emailsToStrings")
  default List<String> emailsToStrings(List<Email> emails) {
    if (emails == null) {
      return List.of();
    }
    return emails.stream().map(Email::getEmail).collect(Collectors.toList());
  }

  @Named("phonesToStrings")
  default List<String> phonesToStrings(List<Phone> phones) {
    if (phones == null) {
      return List.of();
    }
    return phones.stream().map(Phone::getPhone).collect(Collectors.toList());
  }

  @Named("networksToStrings")
  default List<String> networksToStrings(List<SocialNetwork> networks) {
    if (networks == null) {
      return List.of();
    }
    return networks.stream().map(SocialNetwork::getAccount).collect(Collectors.toList());
  }
}
