package com.burdzi0.rest.security;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.RestAuthenticator;
import org.pac4j.sparkjava.CallbackRoute;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.post;

public class BasicAuthConfigFactory implements ConfigFactory {

	// It is not ready for usage
	// TODO

	@Override
	public Config build(Object... objects) {
		DirectBasicAuthClient basicAuthClient = new DirectBasicAuthClient(
				new RestAuthenticator("localhost:8080/wtf")
		);
		Config config = new Config(basicAuthClient);
		config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));

		Route callback = new CallbackRoute(config);
		get("/callback", callback);
		post("/callback", callback);
		return config;
	}
}
