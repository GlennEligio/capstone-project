package com.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.model.Account;
import com.model.Transaction;
import com.model.User;

@Controller
public class TransferController {

	@Autowired
	private UserDelegate userDelegate;
	@Autowired
	private AccountDelegate accountDelegate;
	@Autowired
	private TransactionDelegate transactionDelegate;

	// TRANSACTION RELATED
	@GetMapping("/transfer")
	public String transferMoney(Model model, HttpServletRequest request) {
		// Redirect User if no User is stored in Session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if (null == user) {
			return "redirect:/login";
		}
		
		Boolean userStatus = userDelegate.getStatus();
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean transactionStatus = transactionDelegate.getStatus();
		
		if(!userStatus || !accountStatus || !transactionStatus) {
			return "service-down";
		}
		
		List<Account> accounts = accountDelegate.getAccountsOfUser(user.getUsername());
		model.addAttribute("transaction", new Transaction());
		model.addAttribute("accounts", accounts);
		return "transfer";
	}

	@PostMapping("/transfer")
	public String transferMoney(@Valid @ModelAttribute Transaction transaction, BindingResult result) {

		if (result.hasErrors()) {
			return "transfer";
		}
		
		Boolean userStatus = userDelegate.getStatus();
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean transactionStatus = transactionDelegate.getStatus();
		
		if(!userStatus || !accountStatus || !transactionStatus) {
			return "service-down";
		}
		
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(transaction.getToAccount());
		accounts.add(transaction.getFromAccount());
		List<String> usernames = accountDelegate.transferUpdateAccounts(accounts, transaction.getAmount());
		if (usernames != null) {
			transactionDelegate.addTransaction(transaction);
			userDelegate.transferUpdateTotalBal(usernames, transaction.getAmount());
			return "transfer-success";
		}
		return "transfer-fail";
	}
}
