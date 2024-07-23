package com.UserManagementSystem.UserManagementSystemSpringBoot.JWTSecurity;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private String secret;
	private int jwtExpirationInMs;
	public String getSecret() {
		System.out.println("Secret: "+secret);
		return secret;
	}
	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public int getJwtExpirationInMs() {
		System.out.println("jwtExpirationInMs: "+jwtExpirationInMs);
		return jwtExpirationInMs;
	}
	@Value("${jwt.jwtExpirationInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}
	
	public String generateToken(UserDetails userDetails)
	{
		Map<String,Object> claims = new HashMap<>();
		 Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		 
		 return doGenerateToken(claims,userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String,Object> claims,String subject)
	{
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Boolean validateJwtToken(String token, UserDetails userDetails) { 
	      String username = getUsernameFromToken(token); 
	      Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	      Boolean isTokenExpired = claims.getExpiration().before(new Date()); 
	      return (username.equals(userDetails.getUsername()) && !isTokenExpired); 
	   } 
	   public String getUsernameFromToken(String token) {
	      final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); 
	      return claims.getSubject(); 
	   }

}
