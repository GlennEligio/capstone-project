package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.delegate.UserDelegate;
import com.model.User;

@Controller
public class LoginController {
	@Autowired
	private UserDelegate userDelegate;

	// REDIRECT
	@GetMapping("/actuator/info")
	public String redirect() {
		return "redirect:/login";
	}

	// USER RELATED
	@GetMapping("/login")
	public String login(Authentication authentication) {
		if(authentication!=null) {
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
				return "redirect:/admin";
			}
			else {
				return "redirect:/home";
			}
		}
		return "login";
	}
}
