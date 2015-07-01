package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.ArrivalsDeparturesDao;
import com.bionic.bookoffice.persistance.dao.FlightsDao;
import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.utils.TicketsException;

@Named
public class FlightsServiceImpl implements FlightsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private FlightsDao flightsDao;
	@Inject
	private ArrivalsDeparturesDao arrivalsDeparturesDao;

	@Override
	public Flights findById(long id) {
		return flightsDao.findById(id);
	}

	@Transactional
	@Override
	public void save(Flights flights) {
		flightsDao.save(flights);
	}

	@Transactional
	@Override
	public void delete(long id) {
		flightsDao.delete(id);
	}

	@Transactional
	@Override
	public void edit(Flights flights) {
		flightsDao.edit(flights);
	}

	@Override
	public List<Flights> findFlightsByLocalDateTimeCityArrCityDep(
			LocalDateTime dateTime, String arrivalPlace, String departurePlace) {
		List<ArrivalsDepartures> arrival = arrivalsDeparturesDao
				.findByCity(arrivalPlace);
		List<ArrivalsDepartures> departure = arrivalsDeparturesDao
				.findByCity(departurePlace);
		List<Flights> flights = new ArrayList<>();
		for (int i = 0; i < arrival.size(); i++) {
			for (int j = 0; j < departure.size(); j++) {
				flights.addAll(flightsDao
						.findFlightsByLocalDateTimeAirportArrAirportDep(
								dateTime, arrival.get(i), departure.get(j)));
			}
		}
		return flights;
	}

	@Override
	public List<Flights> findFlightsByLocalDateTimeCityArrCityDepForMonth(
			LocalDateTime dateTime, String arrivalPlace, String departurePlace) {
		List<ArrivalsDepartures> arrival = arrivalsDeparturesDao
				.findByCity(arrivalPlace);
		List<ArrivalsDepartures> departure = arrivalsDeparturesDao
				.findByCity(departurePlace);
		List<Flights> flights = new ArrayList<>();
		for (int i = 0; i < arrival.size(); i++) {
			for (int j = 0; j < departure.size(); j++) {
				flights.addAll(flightsDao
						.findFlightsByLocalDateTimeAirportArrAirportDepForMonth(
								dateTime, arrival.get(i), departure.get(j)));
			}
		}
		return flights;
	}

	@Transactional
	@Override
	public void closeFlight(Flights flight) {
		flightsDao.closeFlight(flight);
	}

	@Transactional
	@Override
	public void setAmountOfTickets(Flights flight, int amountOfTickets)
			throws TicketsException {
		flightsDao.setAmountOfTickets(flight, amountOfTickets);
	}

	@Override
	public List<Flights> findFlightsByLocalDateLocalDateCityFromCityTo(
			Date dateTimeFrom, Date dateTimeTo, String cityFrom, String cityTo) {
		List<ArrivalsDepartures> cityListFrom = arrivalsDeparturesDao
				.findByCity(cityFrom);
		List<ArrivalsDepartures> cityListTo = arrivalsDeparturesDao
				.findByCity(cityTo);
		List<Flights> flights = new ArrayList<>();
		for (int i = 0; i < cityListFrom.size(); i++) {
			for (int j = 0; j < cityListTo.size(); j++) {
				flights.addAll(flightsDao.findFlightsFromAirportToAirport(
						cityListFrom.get(i), cityListTo.get(j)));
			}
		}

		for (int i = 0; i < flights.size(); i++) {
			Date d = flights.get(i).getDepartureDate();
			if (!(d.after(dateTimeFrom) && d.before(dateTimeTo))) {
				flights.remove(i);
			}
		}
		return flights;
	}

	@Transactional
	@Override
	public void changeAmountOfTickets(Flights flight, int amountOfTickets) {
		flightsDao.changeAmountOfTickets(flight, amountOfTickets);
	}
}
