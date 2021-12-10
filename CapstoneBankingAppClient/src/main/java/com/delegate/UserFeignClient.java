package com.delegate;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.AuthenticationRequest;
import com.model.AuthenticationResponse;
import com.model.User;

@FeignClient(name = "user-service")
public interface UserFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "/users/")
	public ResponseEntity<List<User>> getAllUser(@RequestHeader("Authorization") String token);
	
	@RequestMapping(method = RequestMethod.GET, value = "users/search")
	public ResponseEntity<List<User>> getSearchResult(@RequestParam("query") String username, 
														@RequestHeader("Authorization") String token);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/recent")
	public ResponseEntity<List<User>> getRecentUser(@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.GET, value = "/users/status")
	public ResponseEntity<Boolean> getStatus();

	@RequestMapping(method = RequestMethod.POST, value = "/users/authenticate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);

	@RequestMapping(method = RequestMethod.POST, value = "/users/login")
	public ResponseEntity<User> loginValidate(User user, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.POST, value = "/users/register")
	public ResponseEntity<User> registerUser(User user);

	@RequestMapping(method = RequestMethod.PUT, value = "/users/")
	public ResponseEntity<?> updateUser(User user, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.PUT, value = "/users/transfer/{amount}")
	public ResponseEntity<?> transferUpdateTotalBal(List<String> usernames, 
													@PathVariable("amount") Integer amount,
													@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.PUT, value = "/users/{username}/{action}/{amount}")
	public ResponseEntity<?> updateTotalBal(@PathVariable("username") String username,
											@PathVariable("action") String action, 
											@PathVariable("amount") Integer amount,
											@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.PUT, value = "/user/update/{action}")
	public ResponseEntity<?> updateListAndNumberOfAccount(User user, 
															@PathVariable("action") String action,
															@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username
										, @RequestHeader("Authorization") String token);
}
