package ua.com.javarush.gnew.contactm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ContactManagerSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContactManagerSpringBootApplication.class, args);
  }
}
