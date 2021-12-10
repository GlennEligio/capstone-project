package com.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.delegate.TransactionDelegate;
import com.model.Transaction;
import com.model.User;

@Controller
public class UserHistoryController {

	@Autowired
	private TransactionDelegate transactionDelegate;

	@GetMapping("/user-history")
	public String userHistory(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if (null == user) {
			return "redirect:/login";
		}
		
		Boolean status = transactionDelegate.getStatus();
		if(!status) {
			return "service-down";
		}
		
		// Get account id as array from User object
		List<String> accountIdString = Arrays.asList(user.getListOfAccounts().split(","));

		// Convert String account ids to Integer
		List<Integer> accountIds = accountIdString.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());

		// Get Transaction that where Account Id is present in toAccount and fromAccount
		List<Transaction> userHistory = transactionDelegate.getUserTransaction(user, "all");

		// Define the Transaction type, Account Used, and Account Target of Transaction
		// objects
		userHistory = userHistory.stream().map(t -> {
			if (t.getToAccount() != null && t.getFromAccount() != null) {
				Integer to = t.getToAccount().getAccountNumber();
				Integer from = t.getFromAccount().getAccountNumber();
				if (accountIds.contains(to) && accountIds.contains(from)) {
					t.setTransactionType("Self transfer");
					t.setAccountUsed(from);
					t.setAccountTarget(to);
				} else if (accountIds.contains(from) && !accountIds.contains(to)) {
					t.setTransactionType("Send");
					t.setAccountUsed(from);
					t.setAccountTarget(to);
				} else if (accountIds.contains(to) && !accountIds.contains(from)) {
					t.setTransactionType("Receive");
					t.setAccountUsed(to);
					t.setAccountTarget(from);
				}
			} else if (t.getToAccount() != null && t.getFromAccount() == null) {
				Integer to = t.getToAccount().getAccountNumber();
				if (accountIds.contains(to)) {
					t.setTransactionType("Deposit");
					t.setAccountUsed(to);
				}
			} else if (t.getToAccount() == null && t.getFromAccount() != null) {
				Integer from = t.getFromAccount().getAccountNumber();
				if (accountIds.contains(from)) {
					t.setTransactionType("Withdraw");
					t.setAccountUsed(from);
				}
			}
			return t;
		}).collect(Collectors.toList());

		// Add List of Transactions in Model
		model.addAttribute("userHistory", userHistory);
		return "user-history";
	}
}
