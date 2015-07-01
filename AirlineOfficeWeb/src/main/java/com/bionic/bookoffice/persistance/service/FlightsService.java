package com.bionic.bookoffice.persistance.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.utils.TicketsException;

public interface FlightsService {

	public Flights findById(long id);

	public void save(Flights flights);

	public void delete(long id);

	public void edit(Flights flights);

	public List<Flights> findFlightsByLocalDateTimeCityArrCityDep(
			LocalDateTime dateTime, String arrivalPlace, String departurePlace);

	public List<Flights> findFlightsByLocalDateTimeCityArrCityDepForMonth(
			LocalDateTime dateTime, String arrivalPlace, String departurePlace);

	public void closeFlight(Flights flight);

	public void setAmountOfTickets(Flights flight, int amountOfTickets)
			throws TicketsException;
	
	public void changeAmountOfTickets(Flights flight, int amountOfTickets);

	public List<Flights> findFlightsByLocalDateLocalDateCityFromCityTo(
			Date dateTimeFrom, Date dateTimeTo, String cityFrom,
			String cityTo);
}
