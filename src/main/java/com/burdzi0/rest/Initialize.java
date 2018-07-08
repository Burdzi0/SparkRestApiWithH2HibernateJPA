package com.burdzi0.rest;

import com.burdzi0.rest.model.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Initialize {

	private EntityManagerFactory factory;

	@Inject
	public Initialize(EntityManagerFactory factory) {
		this.factory = factory;
	}

	void start() {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query q = manager.createNativeQuery(getSQLFileContent());
		q.executeUpdate();
		manager.persist(new User("XYZ", "PASSWORD", true, 999));
		manager.persist(new User("ABC", "PASSWORD1", true, 998));
		manager.getTransaction().commit();
		manager.close();
	}

	private String getSQLFileContent() {
		ClassLoader classLoader = getClass().getClassLoader();

		String sqlFileName = "sql/start.sql";

		Path path = Optional.ofNullable(classLoader.getResource(sqlFileName))
				.map(URL::getPath)
				.map(Paths::get)
				.orElseThrow(IllegalStateException::new);

		StringBuilder stringBuilder = new StringBuilder();

		try {
			Files.lines(path).forEach(stringBuilder::append);
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't find sql startup file");
		}

		if (stringBuilder.length() == 0)
			throw new IllegalStateException("Couldn't load data from sql startup file");
		return stringBuilder.toString();
	}
}
