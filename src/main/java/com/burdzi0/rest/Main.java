package com.burdzi0.rest;

import com.burdzi0.rest.dao.UserDAO;
import com.burdzi0.rest.dao.UserDAOImpl;
import com.burdzi0.rest.model.User;
import com.burdzi0.rest.response.JSONResponseTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

	private static EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("UsersUnit");
	private static UserDAO userDAO = new UserDAOImpl();

	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.start();
		port(8080);

		get("/", (request, response) -> userDAO.getAllUsers(),
				new JSONResponseTransformer());
	}

	private void start() throws IOException {
		EntityManager manager = sessionFactory.createEntityManager();
		manager.getTransaction().begin();
//		Query q = manager.createNativeQuery("CREATE TABLE USERS(ID INT PRIMARY KEY AUTO_INCREMENT , NAME VARCHAR(255), AGE INT);");
//		q.executeUpdate();
		Query q = manager.createNativeQuery(getSQLFileContent());
		q.executeUpdate();
		manager.persist(new User("XYZ", 999));
		manager.persist(new User("ABC", 998));
		manager.getTransaction().commit();
		manager.close();
	}

	private String getSQLFileContent() {
		ClassLoader classLoader = getClass().getClassLoader();
		Path file = Paths.get(classLoader.getResource("sql/start.sql").getPath());
		StringBuilder stringBuilder = new StringBuilder();
		try {
			Files.lines(file).forEach(stringBuilder::append);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (stringBuilder.toString().equals(""))
			throw new IllegalStateException("Couldn't load data from sql startup file");
		return stringBuilder.toString();
	}


}
