package com.UserManagementSystem.UserManagementSystemSpringBoot.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.CustomUserDetails;
import com.UserManagementSystem.UserManagementSystemSpringBoot.JWTSecurity.AuthenticationRequest;
import com.UserManagementSystem.UserManagementSystemSpringBoot.JWTSecurity.AuthenticationResponse;
import com.UserManagementSystem.UserManagementSystemSpringBoot.JWTSecurity.JwtUtil;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Service.CustomUserDetailsService;

@Controller
public class JWTAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String createAuthenticationToken(@RequestParam("username") String email,@RequestParam("password") String password,HttpServletResponse response)
			throws Exception {
		System.out.println("email:"+email);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					email, password));

		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		
		System.out.println("user: "+userDetails.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("token mil gaya:"+token);
		String out = "Bearer ";
		out += token;
		Cookie cookie = new Cookie("Authorization",token);
		response.addCookie(cookie);
		return "loginServlet";

	}

}
