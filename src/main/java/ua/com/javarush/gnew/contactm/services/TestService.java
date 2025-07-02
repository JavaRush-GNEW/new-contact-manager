package ua.com.javarush.gnew.contactm.services;

import org.springframework.stereotype.Service;

@Service
public class TestService {
  public void throwTestException() {
    throw new IllegalArgumentException("Test error");
  }
}
