package com.burdzi0.rest.controller;

import com.burdzi0.rest.dao.UserDAO;
import com.burdzi0.rest.response.JSONResponseTransformer;
import spark.Request;

import static spark.Spark.get;

public class UserController {

	private UserDAO userDAO;


	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
		userGET();
	}

	private void userGET() {
		get("/user", (request, response) -> userDAO.getAllUsers(),
				new JSONResponseTransformer()
		);

		get("/user/admin", (request, response) ->
						userDAO.getAllAdmins(),
				new JSONResponseTransformer()
		);

		get("/user/:id", (request, response) ->
				userDAO.getUserByID(getIdFromParam(request))
						.orElseGet(() -> {
							response.redirect("/user");
							return null;
						}),
				new JSONResponseTransformer());
	}

	private long getIdFromParam(Request request) {
		return Long.parseLong(request.params(":id"));
	}
}
