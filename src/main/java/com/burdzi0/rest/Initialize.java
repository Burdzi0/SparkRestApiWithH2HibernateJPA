package com.burdzi0.rest;

import com.burdzi0.rest.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Initialize {

	private EntityManagerFactory factory;

	public Initialize(EntityManagerFactory factory) {
		this.factory = factory;
	}

	protected void start() throws IOException {
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
		Optional<Path> filePath = Optional.ofNullable(Paths.get(classLoader.getResource(sqlFileName).getPath()));

		StringBuilder stringBuilder = new StringBuilder();
		Path path = filePath.orElseThrow(IllegalStateException::new);

		try {
			Files.lines(path).forEach(stringBuilder::append);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (stringBuilder.toString().equals(""))
			throw new IllegalStateException("Couldn't load data from sql startup file");
		return stringBuilder.toString();
	}
}
