package com.burdzi0.rest;

import com.burdzi0.rest.dao.UserDAO;
import com.burdzi0.rest.dao.UserDAOImpl;
import com.burdzi0.rest.model.User;

import javax.persistence.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

	private static EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("UsersUnit");

	public static void main(String[] args) throws IOException {
		port(8080);
		start();

		UserDAO userDAO = new UserDAOImpl();
		get("/", (request, response) -> "Result: " + findUsers().toString());
	}

	private static List<String> findUsers() {
		EntityManager manager = sessionFactory.createEntityManager();
		List<User> users = manager.createQuery("from User").getResultList();
		return users.stream().map(User::toString).collect(Collectors.toList());
	}

	private static void start() throws IOException {
		EntityManager manager = sessionFactory.createEntityManager();
		manager.getTransaction().begin();
		Query q = manager.createNativeQuery("CREATE TABLE USERS(ID INT PRIMARY KEY AUTO_INCREMENT , NAME VARCHAR(255), AGE INT);");
		q.executeUpdate();
		manager.persist(new User("XYZ", 999));
		manager.persist(new User("ABC", 998));
		manager.getTransaction().commit();
		manager.close();
	}


}
