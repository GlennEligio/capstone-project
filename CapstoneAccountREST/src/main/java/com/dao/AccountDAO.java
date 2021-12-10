package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.Account;

public interface AccountDAO extends JpaRepository<Account, Integer>{

	@Query("SELECT a FROM Account a "
			+ "JOIN a.username u "
			+ "WHERE u.username=?1")
	List<Account> findAccountsWithUsername(String username);
}
