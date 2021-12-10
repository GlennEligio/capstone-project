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
import org.springframework.web.bind.annotation.RequestParam;

import com.delegate.AccountDelegate;
import com.delegate.TransactionDelegate;
import com.delegate.UserDelegate;
import com.model.Account;
import com.model.Transaction;
import com.model.User;

@Controller
public class DepositWithdrawController {

	@Autowired
	private UserDelegate userDelegate;
	@Autowired
	private AccountDelegate accountDelegate;
	@Autowired
	private TransactionDelegate transactionDelegate;

	// ACCOUNT RELATED
	@GetMapping("deposit-account")
	public String depositAccount(@RequestParam("accountNumber") String accountNumber, Model model,
			HttpServletRequest request) {
		// Redirect User if no User is stored in Session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if (null == user) {
			return "redirect:/login";
		}
		Account account = new Account();
		model.addAttribute("deposit-account", account);
		return "deposit-account";
	}

	@PostMapping("/deposit-account")
	public String depositAccount(@Valid @ModelAttribute("deposit-account") Account account, BindingResult result) {
		if (result.hasErrors()) {
			return "deposit-account";
		}
		
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean transactionStatus = transactionDelegate.getStatus();
		if(!accountStatus || !transactionStatus) {
			return "service-down";
		}

		// Update Account balance in Account Service
		Account accountFromDb = accountDelegate.updateAccountBalance(account.getAccountNumber(), account.getAccountBalance());
		if (accountFromDb != null) {

			// Add Transaction to Transaction Service
			Transaction transaction = new Transaction();
			transaction.setToAccount(accountFromDb);
			transaction.setAmount(account.getAccountBalance());
			transactionDelegate.addTransaction(transaction);

			// Update User total balance
			userDelegate.updateTotalBal(accountFromDb.getUsername().getUsername(),
										"deposit",
										account.getAccountBalance());
			return "deposit-success";
		} else {
			return "deposit-fail";
		}
	}

	@GetMapping("withdraw-account")
	public String withdrawAccount(@RequestParam("accountNumber") String accountNumber, Model model,
			HttpServletRequest request) {
		// Redirect User if no User is stored in Session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if (null == user) {
			return "redirect:/login";
		}
		Account account = new Account();
		account.setAccountNumber(Integer.parseInt(accountNumber));
		model.addAttribute("withdraw-account", account);
		return "withdraw-account";
	}

	@PostMapping("/withdraw-account")
	public String withdrawAccount(@Valid @ModelAttribute("withdraw-account") Account account, BindingResult result) {
		if (result.hasErrors()) {
			return "withdraw-account";
		}
		int negativeAmount = account.getAccountBalance() * -1;
		account.setAccountBalance(negativeAmount);

		// Update Account in the Account Service
		Account accountFromDb = accountDelegate.updateAccountBalance(account.getAccountNumber(), account.getAccountBalance());

		if (accountFromDb != null) {
			// Add transaction to Transaction Service
			Transaction transaction = new Transaction();
			transaction.setFromAccount(accountFromDb);
			transaction.setAmount(account.getAccountBalance() * -1);
			transactionDelegate.addTransaction(transaction);

			// Update User total balance
			userDelegate.updateTotalBal(accountFromDb.getUsername().getUsername(),
										"withdraw",
										account.getAccountBalance());
			return "withdraw-success";
		} else {
			return "withdraw-fail";
		}
	}
}
