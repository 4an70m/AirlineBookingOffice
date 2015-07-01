package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.utils.TicketsException;

@Repository
public class FlightsDaoImpl implements FlightsDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Flights findById(long id) {
		return em.find(Flights.class, id);
	}

	@Override
	public void save(Flights flights) {
		em.persist(flights);
	}

	@Override
	public void delete(long id) {
		Flights flights = em.find(Flights.class, id);
		if (flights != null) {
			em.remove(flights);
		}
	}

	@Override
	public void edit(Flights flights) {
		em.merge(flights);
	}

	@Override
	public List<Flights> findFlightsByLocalDateTimeAirportArrAirportDep(
			LocalDateTime dateTime, ArrivalsDepartures arrival,
			ArrivalsDepartures departure) {
		TypedQuery<Flights> query = em
				.createQuery("SELECT f FROM Flights f WHERE f.arrival = ?1"
						+ " AND f.departure = ?2 AND f.isActive = 0 AND "
						+ "Function('Year',f.departureDate) = ?3 AND "
						+ "Function('Month',f.departureDate) = ?4  AND "
						+ "Function('Day',f.departureDate) = ?5", Flights.class);

		query.setParameter(1, arrival);
		query.setParameter(2, departure);
		query.setParameter(3, dateTime.getYear());
		query.setParameter(4, dateTime.getMonthValue());
		query.setParameter(5, dateTime.getDayOfMonth());
		return query.getResultList();
	}

	@Override
	public void changeAmountOfTickets(Flights flight, int deltaAmount) {
		flight = findById(flight.getId());
		flight.setAmountOfTickets(flight.getAmountOfTickets()
				+ deltaAmount);
		edit(flight);
	}

	@Override
	public List<Flights> findFlightsByLocalDateTimeAirportArrAirportDepForMonth(
			LocalDateTime dateTime, ArrivalsDepartures arrival,
			ArrivalsDepartures departure) {
		TypedQuery<Flights> query = em.createQuery(
				"SELECT f FROM Flights f WHERE f.arrival = ?1"
						+ " AND f.departure = ?2 AND f.isActive = 0 AND "
						+ "Function('Year',f.departureDate) = ?3 AND "
						+ "Function('Month',f.departureDate) = ?4",
				Flights.class);

		query.setParameter(1, arrival);
		query.setParameter(2, departure);
		query.setParameter(3, dateTime.getYear());
		query.setParameter(4, dateTime.getMonthValue());
		return query.getResultList();
	}

	@Override
	public void closeFlight(Flights flight) {
		Flights f = em.find(Flights.class, flight.getId());
		if (f != null) {
			f.setIsActive((short) 2);
		}
	}

	@Override
	public void setAmountOfTickets(Flights flight, int amountOfTickets)
			throws TicketsException {
		Integer bookedAmountOfTickets = em
				.createQuery(
						"SELECT sum(b.amountOfTickets) FROM Bookings b, Flights f WHERE b.flightId = ?1",
						Integer.class).setParameter(1, flight.getId())
				.getSingleResult();
		if (amountOfTickets < bookedAmountOfTickets) {
			throw new TicketsException(
					"Amount of booked tickets is greater then new amount of tickets.");
		}
		Flights f = em.find(Flights.class, flight.getId());
		if (f != null) {
			f.setAmountOfTickets(amountOfTickets);
		}
	}

	@Override
	public List<Flights> findFlightsFromAirportToAirport(
			ArrivalsDepartures fromAirport, ArrivalsDepartures toAirport) {
		TypedQuery<Flights> query = em.createQuery(
				"SELECT f FROM Flights f WHERE f.arrival = ?1"
						+ " AND f.departure = ?2 AND f.isActive = 0 ",
				Flights.class);

		query.setParameter(1, toAirport);
		query.setParameter(2, fromAirport);
		return query.getResultList();
	}
}
