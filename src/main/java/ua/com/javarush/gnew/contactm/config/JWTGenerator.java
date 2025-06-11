package ua.com.javarush.gnew.contactm.config;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator {
	public String generateToken(Authentication authentication) {
		String userName = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
		
		String token = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(getSigningKey(), SignatureAlgorithm.HS512)
				.compact();
		return token;
	}
	
	public static Key getSigningKey() {
		return Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes());
	}
	
	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token);
			return true;
		} catch(Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
		}
	}
}
