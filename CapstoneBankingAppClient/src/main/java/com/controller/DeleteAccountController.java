package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.delegate.AccountDelegate;
import com.delegate.UserDelegate;
import com.model.Account;
import com.model.User;

@Controller
public class DeleteAccountController {

	@Autowired
	private UserDelegate userDelegate;
	@Autowired
	private AccountDelegate accountDelegate;

	@PostMapping("/delete-account")
	public String deleteAccount(@RequestParam("accountNumber") String accountNumber) {
		Account account = new Account();
		account.setAccountNumber(Integer.parseInt(accountNumber));

		// check Account and User service status
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean userStatus = userDelegate.getStatus();
		if(!accountStatus || !userStatus) {
			return "service-down";
		}
		
		// Remove Account in Account Service
		Account accountDeleted = accountDelegate.deleteAccount(account.getAccountNumber());

		// Update User in User Service
		if (accountDeleted != null) {
			User user = new User();
			user.setUsername(accountDeleted.getUsername().getUsername());
			user.setListOfAccounts(accountNumber);
			user.setTotalBalance(accountDeleted.getAccountBalance() * -1);
			userDelegate.updateListAndNumberOfAccount(user, "delete");
			return "delete-account-success";
		} else {
			return "delete-account-fail";
		}
	}
}
