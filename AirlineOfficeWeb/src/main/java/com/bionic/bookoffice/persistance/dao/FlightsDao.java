package com.bionic.bookoffice.persistance.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.utils.TicketsException;

public interface FlightsDao {

	public Flights findById(long id);

	public void save(Flights flights);

	public void delete(long id);

	public void edit(Flights flights);

	public List<Flights> findFlightsByLocalDateTimeAirportArrAirportDep(
			LocalDateTime dateTime, ArrivalsDepartures arrival,
			ArrivalsDepartures departure);

	public void changeAmountOfTickets(Flights flight, int deltaAmount);

	public List<Flights> findFlightsByLocalDateTimeAirportArrAirportDepForMonth(
			LocalDateTime dateTime, ArrivalsDepartures arrival,
			ArrivalsDepartures departure);

	public void closeFlight(Flights flight);

	public void setAmountOfTickets(Flights flight, int amountOfTickets)
			throws TicketsException;

	public List<Flights> findFlightsFromAirportToAirport(ArrivalsDepartures fromAirport, ArrivalsDepartures toAirport);
}
