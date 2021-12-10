package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UpdateUserController {

	@Autowired
	private UserDelegate userDelegate;
	@Autowired
	private AccountDelegate accountDelegate;
 
	@GetMapping("/update-user")
	public String updateUser(Model model, HttpServletRequest request) {
		model.addAttribute("updateUser", new User());
		return "update-user";
	}

	@PostMapping("/update-user")
	public String updateUser(@Valid @ModelAttribute("updateUser") User user, BindingResult result,
			HttpServletRequest req) {
		if (result.hasErrors()) {
			return "update-user";
		}
		
		Boolean status = userDelegate.getStatus();
		Boolean status1 = accountDelegate.getStatus();
		if(!status || !status1) {
			return "service-down";
		}

		// Get the User from Session
		User userSession = (User) req.getSession().getAttribute("userSession");

		// Assign the ContactNumber and Password of User based on form input
		userSession.setContactNumber(user.getContactNumber());
		userSession.setPassword(user.getPassword());

		// Update User in UserService and AccountService
		boolean updateResult = userDelegate.updateUser(userSession);
		boolean updateUserInAccountResult = accountDelegate.updateUserInAccount(userSession);
		if (updateResult && updateUserInAccountResult) {
			return "update-success";
		} else {
			return "update-fail";
		}
	}
}
