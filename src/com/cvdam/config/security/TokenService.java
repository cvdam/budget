package com.cvdam.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cvdam.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${cvdam.jwt.expiration}")
	private String expiration;
	
	@Value("${cvdam.jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {
		
		User loggedUser = (User)authentication.getPrincipal();
		Date dateNow = new Date();
		Date dateExpiration = new Date(dateNow.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("Budget API")
				.setSubject(loggedUser.getId().toString())
				.setIssuedAt(dateNow)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserid(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
}
