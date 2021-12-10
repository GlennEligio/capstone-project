package com.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Account;
import com.model.Transaction;
import com.model.User;
import com.service.AccountService;
import com.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AccountService accountService;

	@GetMapping("/status")
	public ResponseEntity<Boolean> getStatus() {
		return ResponseEntity.ok(true);
	}

	@GetMapping("/")
	public ResponseEntity<List<Transaction>> loadTransactions() {
		return ResponseEntity.ok(transactionService.findAll());
	}

	@GetMapping("/{transactionId}")
	public ResponseEntity<Transaction> findTransaction(@PathVariable("transactionId") Integer transactionId) {
		Transaction transactionFromDb = transactionService.findTransaction(transactionId);
		if (null == transactionFromDb) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(transactionFromDb);
	}

	@DeleteMapping("/{transactionId}")
	public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") Integer transactionId) {
		if (transactionService.deleteTransaction(transactionId)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/")
	public ResponseEntity<?> updateTransaction(@RequestBody Transaction transaction) {
		if (transactionService.updateTransaction(transaction.getTransactionId(), transaction) != null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/")
	public ResponseEntity<Transaction> registerTransaction(@RequestBody Transaction transaction) {
		if (transaction.getToAccount() != null) {
			Account toAccount = accountService.findAccount(transaction.getToAccount().getAccountNumber());
			if (toAccount != null) {
				transaction.setToAccount(toAccount);
			}
		}
		if (transaction.getFromAccount() != null) {
			Account fromAccount = accountService.findAccount(transaction.getFromAccount().getAccountNumber());
			if (fromAccount != null) {
				transaction.setFromAccount(fromAccount);
			}
		}

		Transaction transactionSaved = transactionService.addTransaction(transaction);
		return ResponseEntity.ok(transactionSaved);
	}

	@GetMapping("/history/account/{accountNumber}")
	public ResponseEntity<List<Transaction>> getAccountTransaction(@PathVariable("accountNumber") Integer accountNumber) {
		return ResponseEntity.ok(transactionService.findTransactionsByAccount(accountNumber));
	}
	
	@PostMapping("/history/user/{history}")
	public ResponseEntity<List<Transaction>> getUserTransaction(@RequestBody User user, @PathVariable("history") String history) {
		List<Integer> accountNumbers = Arrays.asList(user.getListOfAccounts().split(","))
										.stream()
										.map(Integer::parseInt)
										.collect(Collectors.toList());
		return ResponseEntity.ok(transactionService.findTransactionsByAccountNumbers(accountNumbers, history));
	}
}
