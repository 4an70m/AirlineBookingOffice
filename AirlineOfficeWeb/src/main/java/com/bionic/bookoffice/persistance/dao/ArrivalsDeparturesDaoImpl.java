package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;

@Repository
public class ArrivalsDeparturesDaoImpl implements ArrivalsDeparturesDao,
		Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public ArrivalsDepartures findById(long id) {
		return em.find(ArrivalsDepartures.class, id);
	}

	@Override
	public void save(ArrivalsDepartures arrivalsDepartures) {
		em.persist(arrivalsDepartures);
	}

	@Override
	public void delete(long id) {
		ArrivalsDepartures arrivalsDepartures = em.find(
				ArrivalsDepartures.class, id);
		if (arrivalsDepartures != null) {
			em.remove(arrivalsDepartures);
		}
	}

	@Override
	public void edit(ArrivalsDepartures arrivalsDepartures) {
		em.merge(arrivalsDepartures);
	}

	@Override
	public List<ArrivalsDepartures> findByCity(String city) {
		TypedQuery<ArrivalsDepartures> query = em
				.createQuery(
						"SELECT ad FROM ArrivalsDepartures ad WHERE UPPER(ad.city) = ?1",
						ArrivalsDepartures.class);
		query.setParameter(1, city.toUpperCase());
		return query.getResultList();
	}

	@Override
	public List<ArrivalsDepartures> getAllArrivalsDepartures() {
		return em.createQuery("Select a FROM ArrivalsDepartures a",
				ArrivalsDepartures.class).getResultList();
	}

}
