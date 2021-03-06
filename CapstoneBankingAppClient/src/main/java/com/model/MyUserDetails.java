package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	private String jwt;
	
	public MyUserDetails(User user, String jwt) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		if(user.getRoles() == null || user.getRoles().isBlank()) {
			this.authorities = new ArrayList<>();
		}else {
			this.authorities = Arrays.asList(user.getRoles().split(","))
					.stream()
					.map(roles -> new SimpleGrantedAuthority(roles))
					.collect(Collectors.toList());
		}
		this.active = user.getActive();
		this.jwt = jwt;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "MyUserDetails [username=" + username + ", password=" + password + ", active=" + active
				+ ", authorities=" + authorities + "]";
	}

	
}
