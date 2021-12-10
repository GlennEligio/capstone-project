package com.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.model.Transaction;

public interface TransactionDAO extends JpaRepository<Transaction, Integer>{

	@Query("SELECT t FROM Transaction t "
			+ "LEFT JOIN t.fromAccount fa "
			+ "LEFT JOIN t.toAccount ta "
			+ "WHERE fa.accountNumber=:accountNumber "
			+ "OR ta.accountNumber=:accountNumber")
	List<Transaction> findTransactionByAccount(Integer accountNumber);
	
	@Query("SELECT t FROM Transaction t "
			+ "LEFT JOIN t.toAccount ta "
			+ "LEFT JOIN t.fromAccount fa "
			+ "WHERE fa.accountNumber IN :accountNumbers "
			+ "OR ta.accountNumber IN :accountNumbers "
			+ "ORDER BY t.transactionId DESC")
	List<Transaction> findTransactionByAccountNumbersRecent(Pageable pageable, List<Integer> accountNumbers);
	
	@Query("SELECT t FROM Transaction t "
			+ "LEFT JOIN t.toAccount ta "
			+ "LEFT JOIN t.fromAccount fa "
			+ "WHERE fa.accountNumber IN ?1 "
			+ "OR ta.accountNumber IN ?1 "
			+ "ORDER BY t.transactionId DESC")
	List<Transaction> findTransactionByAccountNumbersAll(List<Integer> accountNumbers);
}
