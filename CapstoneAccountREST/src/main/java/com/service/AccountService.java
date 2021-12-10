package com.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AccountDAO;
import com.model.Account;
import com.model.User;

@Service
public class AccountService {

	@Autowired
	private AccountDAO dao;
	
	public Account addAccount(Account account) {
		return dao.save(account);
	}
	
	public void saveAllAccounts(List<Account> accounts) {
		dao.saveAll(accounts);
	}

	public List<Account> loadAll() {
		List<Account> accounts = (List<Account>) dao.findAll();
		return accounts;
	}

	public Account findAccount(Integer accountId) {
		Optional<Account> op = dao.findById(accountId);
		if (op.isPresent()) {
			return op.get();
		} else {
			return null;
		}
	}

	public Account deleteAccount(Integer accountId) {
		Optional<Account> op = dao.findById(accountId);
		if (op.isPresent()) {
			dao.deleteById(accountId);
			return op.get();
		} else {
			return null;
		}
	}

	public Account updateAccount(Account account) {
		Optional<Account> op = dao.findById(account.getAccountNumber());
		if (op.isPresent()) {
			Account accFromDb = op.get();
			accFromDb.setAccountBalance(account.getAccountBalance());
			accFromDb.setUsername(account.getUsername());
			return dao.save(account);
		}
		return null;
	}
	
	public List<Account> findAccountsWithUsername(String username) {
		return dao.findAccountsWithUsername(username);
	}
}
