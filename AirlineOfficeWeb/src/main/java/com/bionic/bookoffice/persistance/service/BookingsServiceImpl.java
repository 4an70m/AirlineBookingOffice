package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.BookingsDao;
import com.bionic.bookoffice.persistance.dao.FlightsDao;
import com.bionic.bookoffice.persistance.dao.TicketsDao;
import com.bionic.bookoffice.persistance.dao.UsersDao;
import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.ExReportResult;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.entity.SimpleReportResult;
import com.bionic.bookoffice.persistance.entity.Tickets;
import com.bionic.bookoffice.persistance.entity.Users;
import com.bionic.bookoffice.persistance.utils.TicketsException;

@Named
public class BookingsServiceImpl implements BookingsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private BookingsDao bookingsDao;
	@Inject
	private TicketsDao ticketsDao;
	@Inject
	private FlightsDao flightsDao;
	@Inject
	private UsersDao usersDao;

	@Override
	public Bookings findById(long id) {
		return bookingsDao.findById(id);
	}

	@Transactional
	@Override
	public void save(Bookings bookings) {
		bookingsDao.save(bookings);
	}

	@Transactional
	@Override
	public void delete(long id) {
		bookingsDao.delete(id);
	}

	@Transactional
	@Override
	public Bookings edit(Bookings bookings) {
		return bookingsDao.edit(bookings);
	}

	@Transactional
	@Override
	public void bookTicketsForFlight(Users user, Flights flight,
			List<Tickets> tickets) throws TicketsException {
		if (tickets.size() <= 0) {
			throw new TicketsException("Empty tickets booking.");
		}
		flight = flightsDao.findById(flight.getId());
		if (flight.getAmountOfTickets() - tickets.size() < 0) {
			throw new TicketsException(
					"Ordered more tickets then there are left. Ordered: "
							+ tickets.size() + ". Left: "
							+ flight.getAmountOfTickets());
		}
		Bookings booking = new Bookings();
		booking.setAmountOfTickets(tickets.size());
		booking.setBookingStatus((short) 0);
		booking.setDateOfBooking(Timestamp.valueOf(LocalDateTime.now()));
		booking.setFlight(flight);
		booking.setUser(user);
		bookingsDao.save(booking);
		flightsDao.changeAmountOfTickets(flight, -tickets.size());
		for (int i = 0; i < tickets.size(); i++) {
			tickets.get(i).setBooking(booking);
			ticketsDao.save(tickets.get(i));
		}
	}

	@Override
	public List<Bookings> viewBookingsByUser(Users user) {
		return bookingsDao.viewBookingsByUser(user);
	}

	@Transactional
	@Override
	public void unbookBookedTickets(int days) {
		List<Bookings> bookings = bookingsDao.getListOfUnpaidBookings();
		if (bookings.isEmpty()) {
			return;
		}
		int time = days * 60 * 60 * 24 * 1000;
		for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i).getDateOfBooking().getTime()
					- Timestamp.valueOf(LocalDateTime.now()).getTime() < time) {
				// TODO:
				// unbook a ticket
				// change amount of tickets to the flight
				Flights f = flightsDao.findById(bookings.get(i).getId());
				flightsDao.changeAmountOfTickets(f, bookings.get(i)
						.getAmountOfTickets());
				bookingsDao.unbookATicket(bookings.get(i));
			}
		}
	}

	@Transactional
	@Override
	public void setBookedTicketPaid(Bookings booking) {
		bookingsDao.setBookingPaid(booking);
	}

	@Transactional
	@Override
	public List<Bookings> getUserBookingsByUserId(int userId,
			short bookingStatus) {

		return bookingsDao.getUserBookingsByUser(usersDao.findById(userId),
				bookingStatus);
	}

	@Transactional
	@Override
	public void changeBookingStatus(Bookings booking, short status) {
		bookingsDao.changeBookingStatus(booking, status);
	}

	@Transactional
	@Override
	public void closeBookingsByFlightId(long flightId) {
		Flights f = flightsDao.findById(flightId);
		if (f != null) {
			bookingsDao.closeBookingsByFlightId(f);
		}
	}

	@Transactional
	@Override
	public void closeSomeBookingsByFlightId(long flightId, int amountOfClosing) {
		Flights f = flightsDao.findById(flightId);
		if (f != null) {
			bookingsDao.closeSomeBookingsByFlightId(f, amountOfClosing);
		}
	}

	@Override
	public List<Bookings> getActiveBookingsByFlightId(long flightId) {
		Flights f = flightsDao.findById(flightId);
		if (f != null) {
			return bookingsDao.getActiveBookingsByFlightId(f);
		}
		return null;
	}

	@Override
	public List<Bookings> getBookingsBetweenDates(Date valueOf, Date valueOf2) {
		return bookingsDao.getBookingsBetweenDates(valueOf, valueOf2);
	}

	@Override
	public List<SimpleReportResult> getSimpleReportForDate(Date dateFrom,
			Date dateTo, short status) {
		return bookingsDao.getSimpleReportForDate(dateFrom, dateTo, status);
	}

	@Override
	public List<Bookings> getListOfUnpaidBookings() {
		return bookingsDao.getListOfUnpaidBookings();
	}
	
	@Override
	public List<ExReportResult> getExReportForDate(Date dateFrom,
			Date dateTo, short status) {
		return bookingsDao.getExReportForDate(dateFrom, dateTo, status);
	}
}
