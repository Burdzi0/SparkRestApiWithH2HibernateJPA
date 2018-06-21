package com.burdzi0.rest;

import com.burdzi0.rest.controller.UserController;
import com.burdzi0.rest.dao.UserDAO;
import com.burdzi0.rest.dao.UserDAOImpl;
import com.burdzi0.rest.response.JSONResponseTransformer;
import spark.Redirect;
import spark.Request;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static spark.Spark.*;

public class Main {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UsersUnit");

	public static void main(String[] args) throws IOException {
		Initialize init = new Initialize(entityManagerFactory);
		init.start();

		port(8080);

		UserDAO userDao = new UserDAOImpl(entityManagerFactory);
		new UserController(userDao);

		redirect.get("/*", "/user", Redirect.Status.MOVED_PERMANENTLY);
	}


}
