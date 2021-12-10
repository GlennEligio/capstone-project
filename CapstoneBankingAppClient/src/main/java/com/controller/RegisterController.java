package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.delegate.AccountDelegate;
import com.delegate.TransactionDelegate;
import com.delegate.UserDelegate;
import com.model.User;

@Controller
public class RegisterController {
	@Autowired
	private UserDelegate userDelegate;

	@GetMapping("/register-user")
	public String registerUser(Model model, Authentication authentication) {
		if(authentication != null) {
			return "redirect:/home";
		}
		model.addAttribute("newUser", new User());
		return "register-user";
	}

	@PostMapping("/register-user")
	public String registerUser(@Valid @ModelAttribute("newUser") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register-user";
		}

		Boolean status = userDelegate.getStatus();
		if(!status) {
			return "service-down";
		}
		
		// Add User in User Service Db
		User userFromRest = userDelegate.registerUser(user);
		
		// Check the result of Add Call
		if(userFromRest!=null) {
			return "register-success";
		} else {
			model.addAttribute("userStatus", "Username is taken");
			return "register-user";
		}
	}
}
