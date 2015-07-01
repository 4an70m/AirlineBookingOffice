package com.bionic.bookoffice.persistance.service;

import java.sql.Date;
import java.util.List;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.ExReportResult;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.entity.SimpleReportResult;
import com.bionic.bookoffice.persistance.entity.Tickets;
import com.bionic.bookoffice.persistance.entity.Users;
import com.bionic.bookoffice.persistance.utils.TicketsException;

public interface BookingsService {

	public Bookings findById(long id);

	public void save(Bookings bookings);

	public void delete(long id);

	public Bookings edit(Bookings bookings);

	public void bookTicketsForFlight(Users user, Flights flight,
			List<Tickets> tickets) throws TicketsException;

	public List<Bookings> viewBookingsByUser(Users user);	 

	public void unbookBookedTickets(int days);

	public void setBookedTicketPaid(Bookings booking);

	public List<Bookings> getUserBookingsByUserId(int userId, short bookingStatus);
	
	public void changeBookingStatus(Bookings booking, short status);

	void closeBookingsByFlightId(long flightId);
	
	void closeSomeBookingsByFlightId(long flightId, int amountOfClosing);

	public List<Bookings> getActiveBookingsByFlightId(long flightId);

	public List<Bookings> getBookingsBetweenDates(java.sql.Date valueOf,
			java.sql.Date valueOf2);

	public List<SimpleReportResult> getSimpleReportForDate(Date dateFrom, Date dateTo, short status);

	public List<Bookings> getListOfUnpaidBookings();

	public List<ExReportResult> getExReportForDate(Date dateFrom, Date dateTo,
			short status);
}
