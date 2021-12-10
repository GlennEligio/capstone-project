package com.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.AuthenticationRequest;
import com.model.AuthenticationResponse;
import com.model.User;
import com.security.MyUserDetailsService;
import com.service.UserService;
import com.util.JwtUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/status")
	public ResponseEntity<Boolean> getStatus() {
		return ResponseEntity.ok(true);
	}

	@RequestMapping(value = "/authenticate", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(HttpServletRequest request, @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect credentials", e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtUtil.generateToken(userDetails);
		final User user = service.findUser(authenticationRequest.getUsername());
		user.setPassword(null);
		return ResponseEntity.ok(new AuthenticationResponse(token, user));
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> loadUsers() {
		return ResponseEntity.ok(service.loadAll());
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> getSearchResult(@RequestParam("query") String searchQuery) {
		return ResponseEntity.ok(service.searchUser(searchQuery));
	}
	
	
	
	@GetMapping("/recent")
	public ResponseEntity<List<User>> loadRecentUsers(){
		return ResponseEntity.ok(service.getRecentUser());
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> findUser(@PathVariable("username") String username) {
		User userFromDb = service.findUser(username);
		if (null == userFromDb) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(userFromDb);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<?> findUserForJWT(@PathVariable("username") String username){
		User userFromDb = service.findUser(username);
		if (null == userFromDb) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userFromDb);
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginValid(@RequestBody User user) {
		User usr = service.findUser(user.getUsername());
		if (usr != null) {
			if (usr.getUsername().equals(user.getUsername()) && usr.getPassword().equals(user.getPassword())) {
				return ResponseEntity.ok(usr);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		if (service.deleteUser(username)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		if (service.updateUser(user.getUsername(), user) != null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		User usr = service.findUser(user.getUsername());
		if (usr != null) {
			return ResponseEntity.notFound().build();
		}
		user.setCreationDate(UserService.dateToString(LocalDateTime.now()));
		user.setTotalBalance(0);
		user.setListOfAccounts("");
		user.setNumberOfAccounts(0);
		user.setActive(true);
		user.setRoles("ROLE_USER");
		User userSaved = service.addUser(user);
		return ResponseEntity.ok(userSaved);
	}

	@PutMapping("/transfer/{amount}")
	public ResponseEntity<?> transferUpdateTotalBal(@RequestBody List<String> usernames,
			@PathVariable("amount") Integer amount) {
		User user1 = service.findUser(usernames.get(0));
		User user2 = service.findUser(usernames.get(1));
		if (user1 != null && user2 != null) {
			user1.setTotalBalance(user1.getTotalBalance() + amount);
			user2.setTotalBalance(user2.getTotalBalance() - amount);
			service.updateUser(user1.getUsername(), user1);
			service.updateUser(user2.getUsername(), user2);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{username}/{action}/{amount}")
	public ResponseEntity<?> updateTotalBal(@PathVariable("username") String username,
											@PathVariable("action") String action,
											@PathVariable("amount") Integer amount) {
		User usr = service.findUser(username);
		if (usr != null) {
			switch(action) {
			case "deposit":
				usr.setTotalBalance(usr.getTotalBalance()+amount);
			case "withdraw":
				usr.setTotalBalance(usr.getTotalBalance()-amount);
			}
			service.updateUser(usr.getUsername(), usr);
			ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/update/{action}")
	public ResponseEntity<?> updateListAndNumberOfAccount(@RequestBody User user, @PathVariable("action") String action) {
		User userDb = service.findUser(user.getUsername());
		if (userDb != null) {
			if (action.equals("add")) {
				List<String> accNumbers = new ArrayList<String>(Arrays.asList(userDb.getListOfAccounts().split(",")));
				accNumbers.add(user.getListOfAccounts());
				userDb.setListOfAccounts(String.join(",", accNumbers));
				userDb.setNumberOfAccounts(userDb.getNumberOfAccounts() + 1);
				userDb.setTotalBalance(userDb.getTotalBalance() + user.getTotalBalance());
			} else if (action.equals("delete")) {
				List<String> accNumbers = new ArrayList<String>(Arrays.asList(userDb.getListOfAccounts().split(",")));
				accNumbers.remove(user.getListOfAccounts());
				userDb.setListOfAccounts(String.join(",", accNumbers));
				userDb.setNumberOfAccounts(userDb.getNumberOfAccounts() - 1);
				userDb.setTotalBalance(userDb.getTotalBalance() + user.getTotalBalance());
			}
			service.updateUser(userDb.getUsername(), userDb);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
