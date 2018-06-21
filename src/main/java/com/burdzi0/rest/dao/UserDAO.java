package com.burdzi0.rest.dao;

import com.burdzi0.rest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
	Optional<User> getUserByID(long id);
	List<User> getAllUsers();

	List<User> getAllAdmins();
	void addUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
}
