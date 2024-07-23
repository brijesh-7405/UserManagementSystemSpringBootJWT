package com.UserManagementSystem.UserManagementSystemSpringBoot.JWTSecurity;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.CustomUserDetails;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();
		String username = null;
		  String token = null;
		if(cookies!=null)
		{
			for(Cookie cookie : cookies)
			{
				System.out.println("cookie name: "+cookie.getName());
				if(cookie.getName().equals("Authorization"))
				{
					token = cookie.getValue();
					try {
			            username = jwtutil.getUsernameFromToken(token);
			         } catch (IllegalArgumentException e) {
			            System.out.println("Unable to get JWT Token");
			         } catch (ExpiredJwtException e) {
			            System.out.println("JWT Token has expired");
			         }
				}
			}
			
		}
		else {
	         System.out.println("Bearer String not found in token");
	      }
	      
//		String tokenHeader = request.getHeader("Authorization");
//	      
//	    
//	      if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//	         token = tokenHeader.substring(7);
//	         try {
//	            username = jwtutil.getUsernameFromToken(token);
//	         } catch (IllegalArgumentException e) {
//	            System.out.println("Unable to get JWT Token");
//	         } catch (ExpiredJwtException e) {
//	            System.out.println("JWT Token has expired");
//	         }
//	      } 
	      if (null != username &&SecurityContextHolder.getContext().getAuthentication() == null) {
	    	  System.out.println("validation");
	          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	          if (jwtutil.validateJwtToken(token, userDetails)) {
	             UsernamePasswordAuthenticationToken
	             authenticationToken = new UsernamePasswordAuthenticationToken(
	             userDetails, null,
	             userDetails.getAuthorities());
	             authenticationToken.setDetails(new
	             WebAuthenticationDetailsSource().buildDetails(request));
	             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	          }
	       }
	       filterChain.doFilter(request, response);
		
	}

}
