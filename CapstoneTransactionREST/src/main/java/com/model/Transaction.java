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
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "fromAccount")
	private Account fromAccount;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "toAccount")
	private Account toAccount;

	private Integer amount;

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
