package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// Fetch UserDetails from Authentication
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		HttpSession session = request.getSession();

		// Add Username in the Session attributes
		session.setAttribute("username", userDetails.getUsername());

		// Redirect User to home page
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			response.sendRedirect(request.getContextPath() + "/admin");
		} else {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

}
