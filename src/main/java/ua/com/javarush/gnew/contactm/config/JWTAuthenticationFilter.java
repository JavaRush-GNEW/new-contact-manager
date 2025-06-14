package ua.com.javarush.gnew.contactm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.javarush.gnew.contactm.services.CustomUserDetailsService;

@Slf4j
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
    log.debug("==> JWT фільтр спрацював");
    log.debug("Authorization header: {}", request.getHeader("Authorization"));
    log.debug("Token parsed: {}", token);
    if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
      String username = tokenGenerator.getUsernameFromJWT(token);
      log.debug("Token valid, username: {}", username);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(context);

      log.debug("==> Користувач аутентифікований");
    } else {
      log.debug("==> Токен відсутній або недійсний");
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
