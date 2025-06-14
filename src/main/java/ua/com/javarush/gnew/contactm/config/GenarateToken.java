package ua.com.javarush.gnew.contactm.config;

import java.security.SecureRandom;

public class GenarateToken {
  private static final String CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int SECRET_LENGTH = 64;

  public static void main(String[] args) {
    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder(SECRET_LENGTH);
    for (int i = 0; i < SECRET_LENGTH; i++) {
      sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    System.out.println("JWT Secret: " + sb.toString());
  }
}
