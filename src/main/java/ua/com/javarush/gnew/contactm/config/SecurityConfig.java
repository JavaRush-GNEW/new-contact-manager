package ua.com.javarush.gnew.contactm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.com.javarush.gnew.contactm.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final JwtAuthEntryPoint authEntryPoint;
  private final CustomUserDetailsService userDetailsService;
  private final JWTGenerator tokenGenerator;

  public SecurityConfig(
      JwtAuthEntryPoint authEntryPoint,
      CustomUserDetailsService userDetailsService,
      JWTGenerator tokenGenerator) {
    this.authEntryPoint = authEntryPoint;
    this.userDetailsService = userDetailsService;
    this.tokenGenerator = tokenGenerator;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())

        //        .csrf(csrf -> csrf
        //            .ignoringRequestMatchers("/api/**") // Вимикаємо CSRF для API
        //        )
        .sessionManagement(
            session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS) // Без сесій, як годиться для JWT
            )
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/main.css", "/img/**", "/register", "/login", "/", "/api/v1/auth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .exceptionHandling(
            ex -> ex.authenticationEntryPoint(authEntryPoint) // кастомний EntryPoint
            )
        .addFilterBefore(
            jwtAuthenticationFilter(tokenGenerator, userDetailsService),
            UsernamePasswordAuthenticationFilter.class); // додаємо JWT фільтр

    return http.build();
  }

  // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  //    http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
  //    .authorizeHttpRequests(
  //            requests ->
  //                requests
  //                    .requestMatchers( "/main.css", "/img/**", "/register", "/login", "/api/**",
  // "/")
  //                    .permitAll()
  //                    .requestMatchers("/").authenticated()
  //                    .anyRequest()
  //                    .authenticated())
  //        .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
  //        .logout(logout -> logout.logoutUrl("/logout"));
  //      return http.build();
  //  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter(
      JWTGenerator tokenGenerator, CustomUserDetailsService userDetailsService) {
    return new JWTAuthenticationFilter(tokenGenerator, userDetailsService);
  }
}
