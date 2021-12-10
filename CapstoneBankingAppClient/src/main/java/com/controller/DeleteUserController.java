package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.delegate.AccountDelegate;
import com.delegate.TransactionDelegate;
import com.delegate.UserDelegate;
import com.model.User;

public class DeleteUserController {
	@Autowired
	private UserDelegate userDelegate;

	@GetMapping("/delete-user")
	public String deleteUser(HttpServletRequest request) {
		// Redirect User if no User is stored in Session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if (null == user) {
			return "redirect:/login";
		}
		
		// Check if User is up
		Boolean status = userDelegate.getStatus();
		if(!status) {
			return "service-down";
		}

		// Delete User
		boolean result = userDelegate.deleteUser(user.getUsername());
		if (result) {
			return "delete-success";
		} else {
			return "delete-fail";
		}
	}
}
