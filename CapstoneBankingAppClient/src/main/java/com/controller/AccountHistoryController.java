package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.constants.SessionAttributesConstant;
import com.delegate.TransactionDelegate;
import com.model.Account;
import com.model.Transaction;
import com.model.User;

@Controller
public class AccountHistoryController {

	@Autowired
	private TransactionDelegate transactionDelegate;

	@GetMapping("/account-history")
	public String accountHistory(@SessionAttribute(name = SessionAttributesConstant.USER_SESSION) User user,
									@RequestParam("accountNumber") String accountNumber, 
									Model model) {
		// Redirect User if no User is stored in Session
		if (null == user) {
			return "redirect:/login";
		}

		// Redirect User if TransactionService is down
		Boolean status = transactionDelegate.getStatus();
		if (!status) {
			return "service-down";
		}

		Account account = new Account();
		account.setAccountNumber(Integer.parseInt(accountNumber));
		List<Transaction> transactions = transactionDelegate.getAccountTransaction(account.getAccountNumber());

		// Filter Transaction to get SendTransaction
		List<Transaction> sendTransactions = transactions.stream().filter(t -> t.getFromAccount() != null)
				.filter(t -> t.getFromAccount().getAccountNumber().equals(account.getAccountNumber()))
				.collect(Collectors.toList());

		// Filter Transaction to get ReceiveTransaction
		List<Transaction> receiveTransactions = transactions.stream().filter(t -> t.getToAccount() != null)
				.filter(t -> t.getToAccount().getAccountNumber().equals(account.getAccountNumber()))
				.collect(Collectors.toList());

		model.addAttribute("fromTransactions", sendTransactions);
		model.addAttribute("toTransactions", receiveTransactions);
		return "account-history";
	}
}
