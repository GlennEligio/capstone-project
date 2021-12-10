package com.delegate;

import java.net.ConnectException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.constants.AuthConfigConstant;
import com.model.AuthenticationRequest;
import com.model.AuthenticationResponse;
import com.model.MyUserDetails;
import com.model.User;

import feign.FeignException;
import feign.FeignException.FeignClientException;

@Service
public class UserDelegate {

	@Autowired
	UserFeignClient userClient;
	
	public boolean getStatus() {
		try {
			return userClient.getStatus().getBody();
		} catch (Exception e) {
			return false;
		}
	}
	
	public AuthenticationResponse getToken(AuthenticationRequest request) {
		try {
			return userClient.authenticate(request).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
 
	public User loginValidate(User user) {
		User usr = new User();
		try {
			usr = userClient.loginValidate(user, getToken()).getBody();
			return usr;
		} catch (Exception e) {
			return null;
		}
	}

	public List<User> getAllUser() {
		List<User> users = userClient.getAllUser(getToken()).getBody();
		return users;
	}
	
	public List<User> getSearchResult(String query){
		return userClient.getSearchResult(query, getToken()).getBody();
	}
	
	public List<User> getRecentUser(){
		List<User> users = userClient.getRecentUser(getToken()).getBody();
		return users;
	}

	public User getUser(String username) {
		User usr = userClient.getUser(username, getToken()).getBody();
		return usr;
	}

	public User registerUser(User user) {
		try {
			System.out.println(user);
			User usr = userClient.registerUser(user).getBody();
			System.out.println("USER RECEIVED " + usr);
			return usr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteUser(String username) {
		try {
			int status = userClient.deleteUser(username, getToken()).getStatusCodeValue();
			if (204 == status) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateUser(User user) {
		try {
			int status = userClient.updateUser(user, getToken()).getStatusCodeValue();
			if (204 == status) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateTotalBal(String username, String action, Integer amount) {
		try {
			int status = userClient.updateTotalBal(username, action, amount, getToken()).getStatusCodeValue();
			if (204 == status) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean transferUpdateTotalBal(List<String> usernames, Integer amount) {
		try {
			int status = userClient.transferUpdateTotalBal(usernames, amount, getToken()).getStatusCodeValue();
			if (204 == status) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateListAndNumberOfAccount(User user, String action) {
		try {
			int status = userClient.updateListAndNumberOfAccount(user, action, getToken()).getStatusCodeValue();
			if (204 == status) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
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
