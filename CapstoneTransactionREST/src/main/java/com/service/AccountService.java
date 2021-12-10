package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AccountDAO;
import com.model.Account;

@Service
public class AccountService {

	@Autowired
	private AccountDAO dao;

	public void updateAccounts(List<Account> accounts) {
		dao.saveAll(accounts);
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
}
