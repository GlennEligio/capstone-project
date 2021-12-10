package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.delegate.AccountDelegate;
import com.delegate.TransactionDelegate;
import com.delegate.UserDelegate;
import com.model.Account;
import com.model.Transaction;
import com.model.User;

@Controller
public class HomeController {
	@Autowired
	private UserDelegate userDelegate;
	@Autowired
	private AccountDelegate accountDelegate;
	@Autowired
	private TransactionDelegate transactionDelegate;

	@GetMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		// Redirect User if no User is stored in Session
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		// Check if UserService is up
		Boolean userStatus = userDelegate.getStatus();
		if (!userStatus) {
			return "service-down";
		}

		// Fetch User, Accounts, and Transaction
		User usr = userDelegate.getUser(username);

		// Set the user from REST into the session attribute
		session.setAttribute("userSession", usr);

		// Initialize List of Accounts and Transaction
		List<Account> accounts = new ArrayList<Account>();
		List<Transaction> recentTrans = new ArrayList<Transaction>();

		// Check if Account Service is up or not
		Boolean accountStatus = accountDelegate.getStatus();
		Boolean transactionStatus = transactionDelegate.getStatus();

		if (accountStatus) {
			// If Account is up, update User details related to its Accounts
			accounts = accountDelegate.getAccountsOfUser(usr.getUsername());

			// List of Accounts received
			Integer numAccount = 0;
			StringBuffer listAccount = new StringBuffer("");
			Integer totalBal = 0;

			for (Account account : accounts) {
				if (listAccount.toString().equals("")) {
					listAccount.append(account.getAccountNumber());
				} else {
					listAccount.append("," + account.getAccountNumber());
				}
				numAccount++;
				totalBal += account.getAccountBalance();
			}

			usr.setListOfAccounts(listAccount.toString());
			usr.setNumberOfAccounts(numAccount);
			usr.setTotalBalance(totalBal);

			// Send updated user to database
			userDelegate.updateUser(usr);
		}
		if (transactionStatus) {
			recentTrans = transactionDelegate.getUserTransaction(usr, "recent");
		}

		// Update Number of Accounts, List of Accounts, Account Balance of User based on

		// Assign the transactionType, accountUsed, and accountTarget attributes of
		// Model

		// If Account Service is down. If down, use the User's list of Account, if up,
		// use the List<Account>
		final List<Integer> accountIds;
		if (accountStatus) {
			accountIds = accounts.stream().map(a -> a.getAccountNumber()).collect(Collectors.toList());
		} else {
			accountIds = Arrays.asList(usr.getListOfAccounts().split(",")).stream().map(s -> Integer.valueOf(s))
					.collect(Collectors.toList());
		}

		recentTrans = recentTrans.stream().map(t -> {
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

		// Assign usr and list of accounts in Session/Model attributes
		session.setAttribute("userSession", usr);
		model.addAttribute("accountSession", accounts);
		model.addAttribute("recentTransactions", recentTrans);
		return "home";

	}

	@GetMapping("/user-details")
	public String userDetails(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userSession");
		if (null == user) {
			return "redirect:/login";
		}
		return "user-details";
	}
}
