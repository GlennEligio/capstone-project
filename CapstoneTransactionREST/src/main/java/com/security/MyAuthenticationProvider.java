package com.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.delegate.UserFeignClient;
import com.model.MyUserDetails;
import com.model.User;
import com.util.JwtUtil;

@Service
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String jwt = (String) authentication.getCredentials();
		
		try {
			ResponseEntity<User> response = userFeignClient.findUser(username, "Bearer " + jwt);	
			User user = response.getBody();
			MyUserDetails userDetails = new MyUserDetails(user);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				token.setDetails(authentication.getDetails());
				return token;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	

}
