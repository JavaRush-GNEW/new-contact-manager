package ua.com.javarush.gnew.contactm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.javarush.gnew.contactm.services.CustomUserDetailsService;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final JWTGenerator tokenGenerator;

  private final CustomUserDetailsService customUserDetailsService;

  public JWTAuthenticationFilter(
      JWTGenerator tokenGenerator, CustomUserDetailsService customUserDetailsService) {

    this.tokenGenerator = tokenGenerator;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getJWTFromRequest(request);
    System.out.println("==> JWT фільтр спрацював");
    System.out.println("Authorization header: " + request.getHeader("Authorization"));
    System.out.println("Token parsed: " + token);
    if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
      String username = tokenGenerator.getUsernameFromJWT(token);
      System.out.println("Token valid, username: " + username);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      System.out.println("==> Користувач аутентифікований");
    } else {
        System.out.println("==> Токен відсутній або недійсний");
    }
    filterChain.doFilter(request, response);
  }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/api/");
    }

    private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}