package com.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.delegate.UserDelegate;
import com.model.User;

@Controller
public class UpdateUserAdminController {
	
	@Autowired
	private UserDelegate userDelegate;
	
	@GetMapping("/update-user-admin")
	public String updateUserAdmin(@RequestParam("username") String username, Model model) {
		Boolean userStatus = userDelegate.getStatus();
		if(!userStatus) {
			return "service-down";
		}
		
		System.out.println("GET MAPPING UPDATE USER ADMIN " + username);
		
		User user = userDelegate.getUser(username);
		model.addAttribute("updateUser", user);
		
		
		return "update-user-admin";
	}
	
	@PostMapping("/update-user-admin")
	public String updateUserAdmin(@Valid @ModelAttribute("updateUser") User user, BindingResult result) {
		System.out.println("POST UPDATE USER ADMIN " + user);
		
		if(result.hasErrors()) {
			return "update-user-admin";
		}
		
		userDelegate.updateUser(user);
		return "update-success";
	}
}
