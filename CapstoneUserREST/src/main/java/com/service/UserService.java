package com.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;
import com.model.User;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return dao.save(user);
	}

	public List<User> loadAll() {
		List<User> list = (List<User>) dao.findAll();
		return list;
	}

	public User findUser(String uname) {
		Optional<User> op = dao.findById(uname);
		if (op.isPresent()) {
			return op.get();
		} else {
			return null;
		}
	}

	public boolean deleteUser(String uname) {
		if (null != uname) {
			Optional<User> op = dao.findById(uname);
			if (op.isPresent()) {
				dao.delete(op.get());
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public User updateUser(String uname, User user) {
		Optional<User> op = dao.findById(uname);
		if (op.isPresent()) {
			User userFromDb = op.get();
			user.setUsername(userFromDb.getUsername());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return dao.save(user);
		} else {
			return null;
		}
	}
	
	public List<User> getRecentUser(){
		return dao.getRecentUsers(PageRequest.of(0, 10));
	}
	
	@Transactional
	public List<User> searchUser(String searchQuery) {
		return dao.getSearchResult(searchQuery);
	}


	public List<User> loadUsersUsingUsernames(HashSet<String> username) {
		List<User> users = (List<User>) dao.findAllById(username);
		return users;
	}

	public static String dateToString(LocalDateTime date) {
		DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a EEE");
		String str = date.format(dtFormat);
		return str;
	}

	public static LocalDateTime stringToDate(String str) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy EEE hh:mm a");
		LocalDateTime date = LocalDateTime.parse(str, dtf);
		return date;
	}

}
