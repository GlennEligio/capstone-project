package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.delegate.UserDelegate;
import com.model.AuthenticationRequest;
import com.model.AuthenticationResponse;
import com.model.MyUserDetails;

@Service
public class MyAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UserDelegate userDelegate;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Fetch the username and password in Authentication Object
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		// Create new AuthenticationRequest object using the user/pass
		AuthenticationRequest request = new AuthenticationRequest(username, password);
		
		// Fetch the AuthenticationResponse object from REST which contains the User
		AuthenticationResponse response = userDelegate.getToken(request);
		if(response != null) {
			// Define the new Authentication, and its properties, and return it back
			// This Authentication will then be put in the SecurityContext of local thread execution
			// We can access this Authentication using SecurityContext.getContext().getAuthentication()
			MyUserDetails userDetails = new MyUserDetails(response.getUser(), response.getJwt());
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}
		
		// Return null in case response from User REST API is null
		return null;
	}

	// Defines which Authentication implementation this AuthenticationProvider will support
	@Override
	public boolean supports(Class<?> authentication) {
		// In case, we will support UsernamePasswordAuthenticationToken
		// If we use a form login for AuthenticationMethod, it will default to this Authentication implementation
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
