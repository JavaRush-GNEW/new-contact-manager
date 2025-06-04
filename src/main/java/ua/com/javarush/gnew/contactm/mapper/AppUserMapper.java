package ua.com.javarush.gnew.contactm.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.entity.AppUser;

@Mapper(
    componentModel = "spring",
    uses = {ContactBookMapper.class})
public interface AppUserMapper {

  AppUserDTO toDto(AppUser appUser);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  AppUser toEntity(AppUserDTO appUserDTO);

  @AfterMapping
  default void setContactBooks(@MappingTarget AppUser appUser) {
    if (appUser.getContactBooks() != null) {
      appUser.getContactBooks().forEach(contactBook -> contactBook.setOwner(appUser));
    }
  }
}
