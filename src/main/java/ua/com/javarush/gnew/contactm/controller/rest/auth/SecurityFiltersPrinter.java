package ua.com.javarush.gnew.contactm.controller.rest.auth;

import jakarta.servlet.Filter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
public class SecurityFiltersPrinter implements CommandLineRunner {
  @Autowired SecurityFilterChain filterChain;

  @Override
  public void run(String... args) throws Exception {
    List<Filter> filters = filterChain.getFilters();

    filters.forEach(filter -> log.debug(filter.getClass().getSimpleName()));
  }
}
