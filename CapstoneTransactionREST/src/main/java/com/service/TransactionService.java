package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dao.TransactionDAO;
import com.model.Transaction;

@Service
public class TransactionService {

	@Autowired
	private TransactionDAO dao;
	
	public Transaction addTransaction(Transaction transaction) {
		return dao.save(transaction);
	}

	public List<Transaction> findAll() {
		List<Transaction> transactions = (List<Transaction>) dao.findAll();
		return transactions;
	}

	public Transaction findTransaction(Integer transactionId) {
		Optional<Transaction> op = dao.findById(transactionId);
		if (op.isPresent()) {
			return op.get();
		} else {
			return null;
		}
	}

	public boolean deleteTransaction(Integer transactionId) {
		Optional<Transaction> op = dao.findById(transactionId);
		if (op.isPresent()) {
			dao.delete(op.get());
			return true;
		} else {
			return false;
		}
	}

	public Transaction updateTransaction(Integer transactionId, Transaction transaction) {
		Optional<Transaction> op = dao.findById(transactionId);
		if (op.isPresent()) {
			Transaction transactionFromDb = op.get();
			transactionFromDb.setFromAccount(transaction.getFromAccount());
			transactionFromDb.setToAccount(transaction.getToAccount());
			return dao.save(transactionFromDb);
		}
		return null;
	}
	
	public List<Transaction> findTransactionsByAccount(Integer accountNumber){
		List<Transaction> transactions = dao.findTransactionByAccount(accountNumber);
		return transactions;
	}
	
	public List<Transaction> findTransactionsByAccountNumbers(List<Integer> accountNumbers, String history){
		if(history.equals("recent")) {
			return dao.findTransactionByAccountNumbersRecent(PageRequest.of(0, 10), accountNumbers);
		}else {
			return dao.findTransactionByAccountNumbersAll(accountNumbers);
		}
	}
}