package com.burdzi0.rest.dao;

import com.burdzi0.rest.model.User;

import java.util.List;

public interface UserDAO {

	List<User> getAllUsers();
	void addUser(User user);
	void deleteUser(User user);
	void updateUser(User user);

}
