package com.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "capstone_user")
public class User {
	@Id
	@Column(name = "user_username")
	private String username;
	@Column(name = "user_password")
	private String password;
	private String creationDate;
	private Integer numberOfAccounts;
	private String listOfAccounts;
	private Integer totalBalance;
	private String contactNumber;
	private String roles;
	private boolean active;

	@JsonIgnore
	@OneToMany(mappedBy = "username", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<Account> accounts;

	public User() {
		super();
	}

	public User(String password, String creationDate, Integer numberOfAccounts, String listOfAccounts,
			Integer totalBalance, String contactNumber, String roles, boolean active, List<Account> accounts) {
		super();
		this.password = password;
		this.creationDate = creationDate;
		this.numberOfAccounts = numberOfAccounts;
		this.listOfAccounts = listOfAccounts;
		this.totalBalance = totalBalance;
		this.contactNumber = contactNumber;
		this.roles = roles;
		this.active = active;
		this.accounts = accounts;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
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

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", creationDate=" + creationDate
				+ ", numberOfAccounts=" + numberOfAccounts + ", listOfAccounts=" + listOfAccounts + ", totalBalance="
				+ totalBalance + ", contactNumber=" + contactNumber + ", roles=" + roles + ", active=" + active
				+ ", accounts=" + accounts + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accounts, active, contactNumber, creationDate, listOfAccounts, numberOfAccounts, password,
				roles, totalBalance, username);
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
		return Objects.equals(accounts, other.accounts) && active == other.active
				&& Objects.equals(contactNumber, other.contactNumber)
				&& Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(listOfAccounts, other.listOfAccounts)
				&& Objects.equals(numberOfAccounts, other.numberOfAccounts) && Objects.equals(password, other.password)
				&& Objects.equals(roles, other.roles) && Objects.equals(totalBalance, other.totalBalance)
				&& Objects.equals(username, other.username);
	}
}
