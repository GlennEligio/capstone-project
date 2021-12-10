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
import com.model.Transaction;
import com.model.User;

@Service
public class TransactionDelegate {

	@Autowired
	TransactionFeignClient transactionClient;
	
	public boolean getStatus() {
		try {
			return transactionClient.getStatus().getBody();
		} catch (Exception e) {
			return false;
		}
	}

	// Transaction related
	public boolean addTransaction(Transaction transaction) {
		try {
			int status = transactionClient.addTransaction(transaction, getToken()).getStatusCodeValue();
			if (200 == status) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Transaction> getAccountTransaction(Integer accountNumber) {
		try {
			ResponseEntity<List<Transaction>> response = transactionClient.getAccountTransaction(accountNumber, getToken());
			if (response.getStatusCodeValue() == 200) {
				return response.getBody();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Transaction> getUserTransaction(User user, String history){
		try {
			ResponseEntity<List<Transaction>> response = transactionClient.getUserTransaction(user, history, getToken());
			if (response.getStatusCodeValue() == 200) {
				return response.getBody();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
