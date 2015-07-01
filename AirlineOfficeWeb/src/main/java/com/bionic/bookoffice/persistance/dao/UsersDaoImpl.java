package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.Users;

@Repository
public class UsersDaoImpl implements UsersDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;

	@Override
	public Users findById(long id) {
		return em.find(Users.class, id);
	}

	@Override
	public void save(Users users) {
		em.persist(users);
	}

	@Override
	public void delete(long id) {
		Users users = em.find(Users.class, id);
		if (users != null) {
			em.remove(users);
		}
	}

	@Override
	public void edit(Users users) {
		em.merge(users);
	}

	@Override
	public Users findUserByLoginPassword(String login, String password) {
		TypedQuery<Users> t = em.createQuery(
				"SELECT u FROM Users u WHERE u.login = ?1 AND u.password = ?2",
				Users.class);
		t.setParameter(1, login);
		t.setParameter(2, password);
		try {
			Users u = t.getSingleResult();
			return u;
		} catch (Exception e) {
			return null;
		}
	}
}
