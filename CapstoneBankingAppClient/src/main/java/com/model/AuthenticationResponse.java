package com.model;

import java.util.Objects;

public class AuthenticationResponse {
	private String jwt;
	private User user;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String jwt, User user) {
		super();
		this.jwt = jwt;
		this.user = user;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(jwt, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationResponse other = (AuthenticationResponse) obj;
		return Objects.equals(jwt, other.jwt) && Objects.equals(user, other.user);
	}


	

}