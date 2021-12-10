package com.dao;

import org.springframework.data.repository.CrudRepository;

import com.model.User;

public interface UserDAO extends CrudRepository<User, String>{

}
