package com.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class Transaction {
	private Integer transactionId;

	@Valid
	private Account fromAccount;

	@Valid
	private Account toAccount;

	@NotNull(message = "Must have a value")
	@PositiveOrZero(message = "Must enter a postive number")
	private Integer amount;

	private String transactionType;
	private Integer accountUsed;
	private Integer accountTarget;

	public Transaction() {
		super();
	}

	public Transaction(Account fromAccount, Account toAccount, Integer amount) {
		super();
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
	}

	public Integer getTransactionId() {
		return transactionId;
		
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getAccountUsed() {
		return accountUsed;
	}

	public void setAccountUsed(Integer accountUsed) {
		this.accountUsed = accountUsed;
	}

	public Integer getAccountTarget() {
		return accountTarget;
	}

	public void setAccountTarget(Integer accountTarget) {
		this.accountTarget = accountTarget;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", fromAccount=" + fromAccount + ", toAccount="
				+ toAccount + ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, fromAccount, toAccount, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(fromAccount, other.fromAccount)
				&& Objects.equals(toAccount, other.toAccount) && Objects.equals(transactionId, other.transactionId);
	}

}
