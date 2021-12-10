package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.delegate.UserDelegate;
import com.model.User;

@Controller
public class AdminController {
	
	@Autowired
	private UserDelegate userDelegate;

	@GetMapping("/admin")
	public String admin(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		Boolean userStatus = userDelegate.getStatus();
		if(!userStatus) {
			return "service-down";
		}
		
		User user = userDelegate.getUser(username);
		session.setAttribute("userSession", user);
		
		List<User> users = userDelegate.getRecentUser();

		model.addAttribute("users", users);
		return "admin";
	}
}
