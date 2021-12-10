package com.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDAO;
import com.model.User;

@Service
public class UserService {

	@Autowired
	private UserDAO dao;

	public User loginValid(User user) {
		Optional<User> op = dao.findById(user.getUsername());
		if (op.isPresent()) {
			if (op.get().getUsername().equals(user.getUsername())
					&& op.get().getPassword().equals(user.getPassword())) {
				return op.get();
			}
		}
		return null;
	}

	public User registerUser(User user) {
		Optional<User> op = dao.findById(user.getUsername());
		if (op.isPresent()) {
			return null;
		}
		user.setCreationDate(dateToString(LocalDateTime.now()));
		user.setTotalBalance(0);
		user.setListOfAccounts("");
		user.setNumberOfAccounts(0);
		dao.save(user);
		return user;
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

	public boolean updateUser(String uname, User user) {
		Optional<User> op = dao.findById(uname);
		if (op.isPresent()) {
			User userFromDb = op.get();
			user.setUsername(userFromDb.getUsername());
			dao.save(user);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateListOfAccount(String uname, Integer accountID) {
		Optional<User> op = dao.findById(uname);
		if (op.isPresent()) {
			User user = op.get();
			if (null == user.getListOfAccounts()) {
				user.setListOfAccounts(accountID.toString());
			} else {
				user.setListOfAccounts(user.getListOfAccounts().concat("," + accountID));
			}
			dao.delete(op.get());
			dao.save(user);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateTotalBal(String uname, Integer amount) {
		Optional<User> op = dao.findById(uname);
		if (op.isPresent()) {
			User user = op.get();
			user.setTotalBalance(user.getTotalBalance() + amount);
			dao.delete(op.get());
			dao.save(user);
			return true;
		} else {
			return false;
		}
	}

	public List<User> loadUsersUsingUsernames(List<String> username) {
		List<User> users = (List<User>) dao.findAllById(username);
		return users;
	}

	public boolean updateUserInAccountService(List<User> users) {
		dao.saveAll(users);
		return true;
	}

	public static String dateToString(LocalDateTime date) {
		DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy EEE hh:mm a");
		String str = date.format(dtFormat);
		return str;
	}

	public static LocalDateTime stringToDate(String str) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy EEE hh:mm a");
		LocalDateTime date = LocalDateTime.parse(str, dtf);
		return date;
	}
}
