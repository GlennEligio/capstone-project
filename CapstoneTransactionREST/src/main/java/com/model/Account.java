package com.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "accounts")
public class Account {
	@Id
	private Integer accountNumber;
	private Integer accountBalance;

	@JsonIgnore
	@OneToMany(mappedBy = "toAccount", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<Transaction> toTransactions;

	@JsonIgnore
	@OneToMany(mappedBy = "fromAccount", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }) 
	private List<Transaction> fromTransactions;

	public Account() { 
		super();
	}

	public Account(Integer accountBalance, List<Transaction> toTransactions, List<Transaction> fromTransactions) {
		super();
		this.accountBalance = accountBalance;
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
		return Objects.hash(accountBalance, accountNumber, fromTransactions, toTransactions);
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
				&& Objects.equals(toTransactions, other.toTransactions);
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountBalance=" + accountBalance + "]";
	}
}
