package com.burdzi0.rest.dao;

import com.burdzi0.rest.model.User;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO{

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersUnit");

	public UserDAOImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}

	@Override
	public Optional<User> getUserByID(long id) {
		EntityManager manager = factory.createEntityManager();
		User user = manager.find(User.class, id);
		return Optional.ofNullable(user);
	}

	public List<User> getAllUsers() {
		EntityManager manager = factory.createEntityManager();
		Session session = manager.unwrap(Session.class);
		return (List<User>) session.createQuery("from User").list();
	}

	public void addUser(User user) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(user);
		manager.getTransaction().commit();
	}

	public void deleteUser(User user) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.remove(user);
		manager.flush();
		manager.getTransaction().commit();
	}

	public void updateUser(User user) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.merge(user);
		manager.getTransaction().commit();
	}
}
