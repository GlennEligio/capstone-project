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
import org.springframework.web.bind.annotation.SessionAttribute;

import com.constants.SessionAttributesConstant;
import com.delegate.AccountDelegate;
import com.delegate.TransactionDelegate;
import com.delegate.UserDelegate;
import com.model.Account;
import com.model.User;

@Controller
public class AddAccountController {
	@Autowired
	private UserDelegate userDelegate;
	@Autowired
	private AccountDelegate accountDelegate;

	@GetMapping("/add-account")
	public String addAccount(Model model, @SessionAttribute(name = SessionAttributesConstant.USER_SESSION) User user) {
		// Redirect User if no User is stored in Session
		if (null == user) {
			return "redirect:/login";
		}
		
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean userStatus = userDelegate.getStatus();
		if(!userStatus || !accountStatus) {
			return "service-down";
		}
		
		model.addAttribute("add-account", new Account());
		return "add-account";
	}

	@PostMapping("/add-account")
	public String addAccount(@Valid @ModelAttribute("add-account") Account account, BindingResult result,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "add-account";
		}
		
		// Check Account service status
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean userStatus = userDelegate.getStatus();
		if(!userStatus || !accountStatus) {
			return "service-down";
		}
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		account.setUsername(user);

		// Add account to Account Service
		Account accountAdded = accountDelegate.addAccount(account);

		// Update Number and List of Accounts of User in User Service
		if (accountAdded != null) {
			user.setListOfAccounts(String.valueOf(accountAdded.getAccountNumber()));
			user.setTotalBalance(accountAdded.getAccountBalance());
			userDelegate.updateListAndNumberOfAccount(user, "add");
		}
		return "add-account-success";
	}

}
