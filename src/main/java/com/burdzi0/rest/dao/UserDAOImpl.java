package com.burdzi0.rest.dao;

import com.burdzi0.rest.model.User;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO{

	private EntityManagerFactory factory;

	@Inject
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

	@Override
	public List<User> getAllAdmins() {
		CriteriaBuilder criteriaBuilder = factory.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("admin"), Boolean.TRUE));

		return factory.createEntityManager()
				.createQuery(criteriaQuery).getResultList();
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
		manager.remove(manager.contains(user) ? user : manager.merge(user));
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
