package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.Aircrafts;

@Repository
public class AircraftsDaoImpl implements AircraftsDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Aircrafts findById(long id) {
		return em.find(Aircrafts.class, id);
	}

	@Override
	public void save(Aircrafts aircrafts) {
		em.persist(aircrafts);
	}

	@Override
	public void delete(long id) {
		Aircrafts aircrafts = em.find(Aircrafts.class, id);
		if (aircrafts != null) {
			em.remove(aircrafts);
		}
	}

	@Override
	public void edit(Aircrafts aircrafts) {
		em.merge(aircrafts);
	}

	@Override
	public List<Aircrafts> browseAircraftsByNamePattern(String namePattern) {
		TypedQuery<Aircrafts> query = em.createQuery(
				"SELECT a FROM Aircrafts a WHERE UPPER(a.aircraftName) LIKE ?1",
				Aircrafts.class);
		query.setParameter(1, "%" + namePattern.toUpperCase() + "%");
		return query.getResultList();
	}

	@Override
	public List<Aircrafts> getAllAircrafts() {
		return em.createQuery("Select a FROM Aircrafts a", Aircrafts.class).getResultList();
	}

}
