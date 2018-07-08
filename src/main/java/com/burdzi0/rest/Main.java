package com.burdzi0.rest;

import com.burdzi0.rest.controller.UserController;
import com.burdzi0.rest.module.UserModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.concurrent.CompletableFuture;

import static spark.Spark.port;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new UserModule());
		CompletableFuture.runAsync(() -> injector.getInstance(Initialize.class).start());

		port(8080);
		UserController userController = injector.getInstance(UserController.class);
	}


}
