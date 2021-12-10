package com.model;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class Account {
	@NotNull(message = "Must have a value")
	@Positive(message = "Must enter a postive number")
	private Integer accountNumber;

	@NotNull(message = "Must have a value")
	@PositiveOrZero(message = "Must enter a postive number")
	private Integer accountBalance;

	private User username;

	private List<Transaction> toTransactions;

	private List<Transaction> fromTransactions;

	public Account() {
		super();
	}

	public Account(
			@NotNull(message = "Must have a value") @Positive(message = "Must enter a postive number") Integer accountNumber,
			@NotNull(message = "Must have a value") @PositiveOrZero(message = "Must enter a postive number") Integer accountBalance,
			User username, List<Transaction> toTransactions, List<Transaction> fromTransactions) {
		super();
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.username = username;
		this.toTransactions = toTransactions;
		this.fromTransactions = fromTransactions;
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

	public List<Transaction> getToTransactions() {
		return toTransactions;
	}

	public void setToTransactions(List<Transaction> toTransactions) {
		this.toTransactions = toTransactions;
	}

	public List<Transaction> getFromTransactions() {
		return fromTransactions;
	}

	public void setFromTransactions(List<Transaction> fromTransactions) {
		this.fromTransactions = fromTransactions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBalance, accountNumber, fromTransactions, toTransactions, username);
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
				&& Objects.equals(accountNumber, other.accountNumber)
				&& Objects.equals(fromTransactions, other.fromTransactions)
				&& Objects.equals(toTransactions, other.toTransactions) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountBalance=" + accountBalance + ", username="
				+ username + ", toTransactions=" + toTransactions + ", fromTransactions=" + fromTransactions + "]";
	}

	

}
