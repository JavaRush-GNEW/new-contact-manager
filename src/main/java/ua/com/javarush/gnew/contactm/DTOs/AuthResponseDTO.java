package ua.com.javarush.gnew.contactm.DTOs;

import lombok.Getter;

@Getter
public class AuthResponseDTO {

  private String accessToken;
  private String tokenType = "bearer ";

  public AuthResponseDTO(String accessToken) {
    this.accessToken = accessToken;
  }
}
