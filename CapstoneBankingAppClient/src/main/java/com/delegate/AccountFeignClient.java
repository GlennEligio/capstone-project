package com.delegate;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Account;
import com.model.User;

@FeignClient(name = "account-service")
public interface AccountFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "/accounts/status")
	public ResponseEntity<Boolean> getStatus();

	@RequestMapping(method = RequestMethod.GET, value = "/accounts/user/{username}")
	public ResponseEntity<List<Account>> getAccountsOfUser(@PathVariable(name = "username") String username,
															@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.POST, value = "/accounts/")
	public ResponseEntity<Account> addAccount(Account account, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.PUT, value = "/accounts/{accountNumber}/{amount}")
	public ResponseEntity<Account> updateAccountBalance(@PathVariable("accountNumber") Integer accountNumber,
														@PathVariable(name = "amount") Integer amount,
														@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.PUT, value = "/accounts/user")
	public ResponseEntity<?> updateUserInAccount(User user, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.PUT, value = "/accounts/transfer/{amount}")
	public ResponseEntity<List<String>> transferUpdateAccounts(List<Account> accounts,
																@PathVariable("amount") Integer amount,
																@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{accountNumber}")
	public ResponseEntity<Account> deleteAccount(@PathVariable("accountNumber") Integer accountNumber,
													@RequestHeader("Authorization") String token);
}
