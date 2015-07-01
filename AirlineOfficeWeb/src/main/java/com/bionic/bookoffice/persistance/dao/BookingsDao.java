package com.bionic.bookoffice.persistance.dao;

import java.sql.Date;
import java.util.List;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.ExReportResult;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.entity.SimpleReportResult;
import com.bionic.bookoffice.persistance.entity.Users;

public interface BookingsDao {

	public Bookings findById(long id);

	public void save(Bookings bookings);

	public void delete(long id);

	public Bookings edit(Bookings bookings);

	public List<Bookings> viewBookingsByUser(Users user);

	public List<Bookings> getListOfUnpaidBookings();
	
	public void setBookingPaid(Bookings booking);
	
	public void unbookATicket(Bookings booking);

	public List<Bookings> getUserBookingsByUser(Users user, short bookingStatus);
	
	public void changeBookingStatus(Bookings booking, short status);

	public void closeBookingsByFlightId(Flights f);

	public void closeSomeBookingsByFlightId(Flights f, int amountOfClosing);

	public List<Bookings> getActiveBookingsByFlightId(Flights f);

	public List<SimpleReportResult> getSimpleReportForDate(Date dateFrom, Date dateTo, short status);

	public List<Bookings> getBookingsBetweenDates(Date valueOf, Date valueOf2);

	public List<ExReportResult> getExReportForDate(Date dateFrom, Date dateTo,
			short status);
}
