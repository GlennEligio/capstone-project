package com.dao;

import org.springframework.data.repository.CrudRepository;

import com.model.Account;

public interface AccountDAO extends CrudRepository<Account, Integer>{

}
