package com.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.MyUserDetails;
import com.model.User;
import com.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUser(username);
		if(user != null) {
			return new MyUserDetails(user);
		}else {
			throw new UsernameNotFoundException("User not found");
		}
	}

}
