package com.burdzi0.rest.controller;

import com.burdzi0.rest.dao.UserDAO;
import com.burdzi0.rest.model.User;
import com.burdzi0.rest.response.ResponseFactory;
import spark.Request;

import javax.inject.Inject;
import java.util.Optional;

import static spark.Spark.*;

public class UserController {

	private UserDAO userDAO;
	private ResponseFactory factory = new ResponseFactory();

	@Inject
	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
		userGET();
		userDELETE();

		after((request, response) ->
				response.type("application/json"));
	}

	private void userGET() {
		get("/user", (request, response) ->
				factory.standardResponse(response, userDAO.getAllUsers()));

		get("/user/admin", (request, response) -> userDAO.getAllAdmins());

		get("/user/null", (request, response) ->
				factory.standardResponse(response, null)
		);

		get("/user/:id", (request, response) ->
				userDAO.getUserByID(getIdFromParam(request))
						.orElseThrow(UserNotFound::new)
		);

		exception(UserNotFound.class, (exception, request, response) ->
		{
			response.status(404);
			response.type("application/json");
		});
	}

	private void userDELETE() {
		delete("/user/:id", (request, response) -> {
			Optional<User> user = userDAO.getUserByID(getIdFromParam(request));
			user.ifPresentOrElse(
					userDAO::deleteUser,
					UserNotFound::new
			);
			response.status(204);
			return null;
		});
	}

	private long getIdFromParam(Request request) {
		String param = request.params(":id");
		if (param.matches("\\d*")) {
			return Long.parseLong(param);
		}
		return 0;
	}

}
