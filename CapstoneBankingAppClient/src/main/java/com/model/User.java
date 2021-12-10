package com.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class User {

	@NotBlank(message = "Username cant be blank")
	private String username;
	@NotBlank(message = "Password cant be blank")
	private String password;
	private String creationDate;
	private Integer numberOfAccounts;
	private String listOfAccounts;
	private Integer totalBalance;
	@Pattern(regexp = "^(09|\\+639)\\d{9}$", message = "Must be 11 digit that starts with '09'")
	private String contactNumber;
	private String roles;
	private Boolean active;

	public User() {
		super();
	}

	public User(@NotBlank(message = "Username cant be blank") String username,
			@NotBlank(message = "Password cant be blank") String password, String creationDate,
			Integer numberOfAccounts, String listOfAccounts, Integer totalBalance,
			@Pattern(regexp = "^(09|\\+639)\\d{9}$", message = "Must be 11 digit that starts with '09'") String contactNumber,
			String roles, Boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.creationDate = creationDate;
		this.numberOfAccounts = numberOfAccounts;
		this.listOfAccounts = listOfAccounts;
		this.totalBalance = totalBalance;
		this.contactNumber = contactNumber;
		this.roles = roles;
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getNumberOfAccounts() {
		return numberOfAccounts;
	}

	public void setNumberOfAccounts(Integer numberOfAccounts) {
		this.numberOfAccounts = numberOfAccounts;
	}

	public String getListOfAccounts() {
		return listOfAccounts;
	}

	public void setListOfAccounts(String listOfAccounts) {
		this.listOfAccounts = listOfAccounts;
	}

	public Integer getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Integer totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", creationDate=" + creationDate
				+ ", numberOfAccounts=" + numberOfAccounts + ", listOfAccounts=" + listOfAccounts + ", totalBalance="
				+ totalBalance + ", contactNumber=" + contactNumber + ", roles=" + roles + ", active=" + active + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, contactNumber, creationDate, listOfAccounts, numberOfAccounts, password, roles,
				totalBalance, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(active, other.active) && Objects.equals(contactNumber, other.contactNumber)
				&& Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(listOfAccounts, other.listOfAccounts)
				&& Objects.equals(numberOfAccounts, other.numberOfAccounts) && Objects.equals(password, other.password)
				&& Objects.equals(roles, other.roles) && Objects.equals(totalBalance, other.totalBalance)
				&& Objects.equals(username, other.username);
	}

}
