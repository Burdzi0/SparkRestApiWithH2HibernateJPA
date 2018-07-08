package com.burdzi0.rest.module;

import com.burdzi0.rest.dao.UserDAO;
import com.burdzi0.rest.dao.UserDAOImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserDAO.class).to(UserDAOImpl.class);
	}

	@Provides
	EntityManagerFactory provideEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("UsersUnit");
	}
}
