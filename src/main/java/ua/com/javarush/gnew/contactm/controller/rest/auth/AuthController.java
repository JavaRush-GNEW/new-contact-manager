package ua.com.javarush.gnew.contactm.controller.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.javarush.gnew.contactm.DTOs.AppUserDTO;
import ua.com.javarush.gnew.contactm.DTOs.AuthResponseDTO;
import ua.com.javarush.gnew.contactm.config.JWTGenerator;
import ua.com.javarush.gnew.contactm.entity.AppUser;
import ua.com.javarush.gnew.contactm.entity.UserRole;
import ua.com.javarush.gnew.contactm.mapper.AppUserMapper;
import ua.com.javarush.gnew.contactm.repository.AppUserRepository;
import ua.com.javarush.gnew.contactm.services.AppUserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final AppUserService appUserService;
	private final AppUserMapper appUserMapper;
	private final JWTGenerator jwtGenerator;
	
	public AuthController(AuthenticationManager authenticationManager, AppUserService appUserService,
			AppUserMapper appUserMapper, JWTGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.appUserService = appUserService;
		this.appUserMapper = appUserMapper;
		this.jwtGenerator = jwtGenerator;
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AppUserDTO appUserDTO){
		System.out.println("==> Login запит: " + appUserDTO.getUsername());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserDTO.getUsername(), appUserDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody AppUserDTO appUserDTO){
		if(appUserService.existsByUserName(appUserDTO.getUsername())) {
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}
		appUserService.register(appUserDTO);
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}
}
