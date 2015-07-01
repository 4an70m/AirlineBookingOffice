package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.Tickets;

@Repository
public class TicketsDaoImpl implements TicketsDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Tickets findById(long id) {
		return em.find(Tickets.class, id);
	}

	@Override
	public void save(Tickets tickets) {
		em.persist(tickets);
	}

	@Override
	public void delete(long id) {
		Tickets tickets = em.find(Tickets.class, id);
		if (tickets != null) {
			em.remove(tickets);
		}
	}

	@Override
	public void edit(Tickets tickets) {
		em.merge(tickets);
	}

	@Override
	public List<Tickets> getTicketsByBooking(Bookings b) {
		TypedQuery<Tickets> t = em.createQuery(
				"SELECT t FROM Tickets t WHERE t.booking = ?1", Tickets.class);
		t.setParameter(1, b);
		return t.getResultList();
	}

	@Override
	public void saveTicketsToBooking(List<Tickets> ticketsList) {
		for(Tickets t : ticketsList){
			edit(t);
		}
	}
}
