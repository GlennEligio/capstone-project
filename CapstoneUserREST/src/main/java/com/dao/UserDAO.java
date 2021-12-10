package com.dao;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.User;

public interface UserDAO extends CrudRepository<User, String>{
	
	@Query("SELECT u FROM User u "
			+ "WHERE u.roles = 'ROLE_USER' "
			+ "ORDER BY u.creationDate DESC")
	List<User> getRecentUsers(Pageable pageable);
	
	@Query("FROM User u "
			+ "WHERE u.username LIKE CONCAT('%', :searchQuery, '%') "
			+ "AND u.roles = 'ROLE_USER'"
			+ "ORDER BY u.creationDate DESC")
	List<User> getSearchResult(String searchQuery);
	
	@Query("FROM User u "
			+ "WHERE u.roles = 'ROLE_USER' "
			+ "ORDER BY u.creationDate DESC")
	List<User> getAllUser();
}
