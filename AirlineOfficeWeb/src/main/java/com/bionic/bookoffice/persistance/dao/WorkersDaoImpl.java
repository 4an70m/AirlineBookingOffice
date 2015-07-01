package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.Workers;

@Repository
public class WorkersDaoImpl implements WorkersDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Workers findById(long id) {
		return em.find(Workers.class, id);
	}

	@Override
	public void save(Workers workers) {
		em.persist(workers);
	}

	@Override
	public void delete(long id) {
		Workers workers = em.find(Workers.class, id);
		if (workers != null) {
			em.remove(workers);
		}
	}

	@Override
	public void edit(Workers workers) {
		em.merge(workers);
	}

	@Override
	public List<Workers> browseActiveWorkers() {
		return em.createQuery("SELECT w FROM Workers w WHERE w.isActive = " + Workers.ACTIVE, Workers.class)
				.getResultList();
	}

	@Override
	public void deactivateWorker(Workers worker) {
		Workers w = em.find(Workers.class, worker.getId());
		if (w != null) {
			w.setIsActive((short) 1);
		}
	}

	@Override
	public List<Workers> findWorkersByNameOrSurname(String nameSurname) {
		TypedQuery<Workers> tq = em.createQuery("SELECT w FROM Workers w "
				+ "WHERE w.name = ?1 OR w.surname = ?1", Workers.class);
		tq.setParameter(1, nameSurname);
		return tq.getResultList();
	}
	
	@Override
	public Workers findWorkersByLoginPassword(String login, String password) {
		TypedQuery<Workers> tq = em.createQuery("SELECT w FROM Workers w "
				+ "WHERE w.login = ?1 AND w.password = ?2", Workers.class);
		tq.setParameter(1, login);
		tq.setParameter(2, password);
		try {
			Workers w = tq.getSingleResult();
			return w;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void reactivateAccount(Workers worker) {
		Workers w = em.find(Workers.class, worker.getId());
		if (w != null) {
			w.setIsActive((short) 0);
		}
	}

	@Override
	public List<Workers> browseAllWorkers() {
		return em.createQuery("SELECT w FROM Workers w", Workers.class)
				.getResultList();
	}
}
