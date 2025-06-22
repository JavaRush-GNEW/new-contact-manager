package ua.com.javarush.gnew.contactm.controller.rest.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.DTOs.AuthResponseDTO;
import ua.com.javarush.gnew.contactm.config.JWTGenerator;
import ua.com.javarush.gnew.contactm.mapper.AppUserMapper;
import ua.com.javarush.gnew.contactm.services.AppUserService;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final AppUserService appUserService;
  private final JWTGenerator jwtGenerator;

  public AuthController(
      AuthenticationManager authenticationManager,
      AppUserService appUserService,
      AppUserMapper appUserMapper,
      JWTGenerator jwtGenerator) {
    this.authenticationManager = authenticationManager;
    this.appUserService = appUserService;
    this.jwtGenerator = jwtGenerator;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody AppUserDTO appUserDTO) {
    log.debug("==> Login запит: ", appUserDTO.getUsername());

    if (appUserService.existsByUsername(appUserDTO.getUsername())
        && appUserService.existUsernamePassword(appUserDTO)) {

      log.debug("Коритувач існує, генеруємо токен");

      Authentication authentication;
      authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  appUserDTO.getUsername(), appUserDTO.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = jwtGenerator.generateToken(authentication);
      return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody AppUserDTO appUserDTO) {
    if (appUserService.existsByUsername(appUserDTO.getUsername())) {
      return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }
    appUserService.register(appUserDTO);
    return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
  }
}
