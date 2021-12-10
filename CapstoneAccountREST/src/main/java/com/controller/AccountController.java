package com.controller;

import java.util.ArrayList;
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
import com.model.User;
import com.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/status")
	public ResponseEntity<Boolean> getStatus(){
		return ResponseEntity.ok(true);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Account>> loadAccounts() {
		return ResponseEntity.ok(accountService.loadAll());
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> findAccount(@PathVariable Integer accountNumber) {
		Account accountFromDb = accountService.findAccount(accountNumber);
		if (null == accountFromDb) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accountFromDb);
	}

	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<Account> deleteAccount(@PathVariable Integer accountNumber) {
		Account accountDeleted = accountService.deleteAccount(accountNumber);
		if (accountDeleted != null) {
			return ResponseEntity.ok(accountDeleted);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{accountNumber}/{amount}")
	public ResponseEntity<Account> updateAccountBalance(@PathVariable Integer accountNumber, @PathVariable("amount") Integer amount) {
		Account accountFromDb = accountService.findAccount(accountNumber);
		if(accountFromDb==null) {
			return ResponseEntity.notFound().build();
		}
		accountFromDb.setAccountBalance(accountFromDb.getAccountBalance()+amount);
		return ResponseEntity.ok(accountService.updateAccount(accountFromDb));
	}

	@PostMapping("/")
	public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
		Account accountFromDb = accountService.findAccount(account.getAccountNumber());
		if(accountFromDb==null) {
			account.setAccountBalance(account.getAccountBalance());
			return ResponseEntity.ok(accountService.addAccount(account));
		}
		return ResponseEntity.internalServerError().build();
	}

	// GET ACCOUNTS OF SPECIFIC USER
	@GetMapping("/user/{username}")
	public ResponseEntity<List<Account>> loadAccountsOfUser(@PathVariable("username") String username) {
		List<Account> accounts = accountService.findAccountsWithUsername(username);
		return ResponseEntity.ok(accounts);
	}

	// UPDATE USERS INSIDE ACCOUNT REST SERVICE
	@PutMapping("/user")
	public ResponseEntity<?> updateUserInAccount(@RequestBody User user) {
		List<Account> accounts = accountService.findAccountsWithUsername(user.getUsername());
		if(accounts.size()<=0) {
			return ResponseEntity.notFound().build();
		}
		
		List<Account> accountToUpdate = accounts.stream()
				.map(a -> {
					a.setUsername(user);
					return a;
				})
				.collect(Collectors.toList());
		accountService.saveAllAccounts(accountToUpdate);
		return ResponseEntity.noContent().build();
	}

	// UPDATE ACCOUNT IN A TRANSFER
	@PutMapping("/transfer/{amount}")
	public ResponseEntity<List<String>> transferUpdateAccounts(@RequestBody List<Account> accounts,
			@PathVariable("amount") Integer amount) {
		
		Account toAccount = accounts.get(0);
		Account fromAccount = accounts.get(1);
		Account toAccountDb = accountService.findAccount(toAccount.getAccountNumber());
		Account fromAccountDb = accountService.findAccount(fromAccount.getAccountNumber());
		
		// Check if both accounts exist
		if (toAccountDb!=null && fromAccountDb!=null) {
			
			// Check if theres enough balance on from Account
			if (fromAccountDb.getAccountBalance() >= amount) {
				toAccountDb.setAccountBalance(toAccountDb.getAccountBalance() + amount);
				fromAccountDb.setAccountBalance(fromAccountDb.getAccountBalance() - amount);
				accountService.addAccount(toAccountDb);
				accountService.addAccount(fromAccountDb);
				List<String> users = new ArrayList<String>();
				users.add(toAccountDb.getUsername().getUsername());
				users.add(fromAccountDb.getUsername().getUsername());
				return ResponseEntity.ok(users);
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

}
