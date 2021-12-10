package com.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "capstone_accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountNumber;
	private Integer accountBalance;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "username")
	private User username;

	public Account() {
		super();
	}

	public Account(Integer accountBalance, User username) {
		super();
		this.accountBalance = accountBalance;
		this.username = username;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Integer accountBalance) {
		this.accountBalance = accountBalance;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountBalance=" + accountBalance + ", username="
				+ username + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBalance, accountNumber, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountBalance, other.accountBalance)
				&& Objects.equals(accountNumber, other.accountNumber) && Objects.equals(username, other.username);
	}

}
