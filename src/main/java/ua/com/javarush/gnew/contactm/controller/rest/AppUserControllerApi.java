package ua.com.javarush.gnew.contactm.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.mapper.AppUserMapper;
import ua.com.javarush.gnew.contactm.repository.AppUserRepository;

@RestController
@RequestMapping("/api/v1/appuser")
public class AppUserControllerApi {

  private final AppUserRepository appUserRepository;
  private final AppUserMapper appUserMapper;

  public AppUserControllerApi(AppUserRepository appUserRepository, AppUserMapper appUserMapper) {
    this.appUserRepository = appUserRepository;
    this.appUserMapper = appUserMapper;
  }

  @PostMapping
  public ResponseEntity<AppUserDTO> save(@RequestBody AppUserDTO appUserDTO) {
    AppUser appUser = appUserMapper.toEntity(appUserDTO);
    AppUser saved = appUserRepository.save(appUser);
    return new ResponseEntity<>(appUserMapper.toDto(saved), HttpStatus.CREATED);
  }
}
