package com.delegate;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Account;
import com.model.Transaction;
import com.model.User;

@FeignClient(name = "transaction-service")
public interface TransactionFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/transactions/status")
	public ResponseEntity<Boolean> getStatus();

	@RequestMapping(method = RequestMethod.POST, value = "/transactions/")
	public ResponseEntity<?> addTransaction(Transaction transaction, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/transactions/history/account/{accountNumber}")
	public ResponseEntity<List<Transaction>> getAccountTransaction(@PathVariable("accountNumber") Integer accountNumber,
																	@RequestHeader("Authorization") String token);
	
	@RequestMapping(method = RequestMethod.POST, value = "/transactions/history/user/{history}")
	public ResponseEntity<List<Transaction>> getUserTransaction(User user, 
																@PathVariable("history") String history,
																@RequestHeader("Authorization") String token);

}
