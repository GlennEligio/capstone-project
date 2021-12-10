package com.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.constants.AuthConfigConstant;
import com.model.Account;
import com.model.MyUserDetails;
import com.model.User;

@Service
public class AccountDelegate {

	@Autowired
	AccountFeignClient accountClient;
	
	public boolean getStatus() {
		try {
			return accountClient.getStatus().getBody();
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<Account> getAccountsOfUser(String username) {
		List<Account> accounts = accountClient.getAccountsOfUser(username, getToken()).getBody();
		return accounts;
	}
	
	public Account updateAccountBalance(Integer accountNumber, Integer amount) {
		try {
			Account accountFromDb = accountClient.updateAccountBalance(accountNumber, amount, getToken()).getBody();
			if(accountFromDb!=null) {
				return accountFromDb;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public Account addAccount(Account account) {
		try {
			ResponseEntity<Account> response = accountClient.addAccount(account, getToken());
			if (200 == response.getStatusCodeValue()) {
				return response.getBody();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public Account deleteAccount(Integer accountNumber) {
		try{
			ResponseEntity<Account> response = accountClient.deleteAccount(accountNumber, getToken());
			if(200 == response.getStatusCodeValue()) {
				return response.getBody();
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}
	
	public boolean updateUserInAccount(User user) {
		try{
			int status = accountClient.updateUserInAccount(user, getToken()).getStatusCodeValue();
			if(204==status) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}
	
	public List<String> transferUpdateAccounts(List<Account> accounts, Integer amount) {
		try{
			List<String> usernames = accountClient.transferUpdateAccounts(accounts, amount, getToken()).getBody();
			return usernames;
		}catch(Exception e) {
			return null;
		}
	}
	
	public String getToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
			return AuthConfigConstant.TOKEN_PREFIX + (String) userDetails.getJwt();
		}
		return null;
	}
	
}
