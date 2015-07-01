package com.bionic.bookoffice.web;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.entity.Tickets;
import com.bionic.bookoffice.persistance.entity.Users;
import com.bionic.bookoffice.persistance.service.BookingsService;
import com.bionic.bookoffice.persistance.service.FlightsService;
import com.bionic.bookoffice.persistance.service.TicketsService;

@Named
@Scope("session")
public class CartBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Bookings> bookingsList;
	private List<Tickets> ticketsList;
	private List<TicketModel> ticketsModelList;
	private int userId = -1;
	private Bookings booking;
	private boolean ticketsLess;
	private List<Bookings> bookingsHistory;

	@Inject
	private BookingsService bookingsService;
	@Inject
	private FlightsService flightsService;
	@Inject
	private TicketsService ticketsService;
	private Bookings acceptedBooking;

	public CartBean() {
		bookingsList = new ArrayList<>();
	}

	public List<Bookings> getBookingsList() {
		return bookingsList;
	}

	public void setBookingsList(List<Bookings> bookingsList) {
		this.bookingsList = bookingsList;
	}

	public List<Tickets> getTicketsList() {
		return ticketsList;
	}

	public List<Bookings> getBookingsHistory() {
		return bookingsHistory;
	}

	public void setBookingsHistory(List<Bookings> bookingsHistory) {
		this.bookingsHistory = bookingsHistory;
	}

	public void setTicketsList(List<Tickets> ticketsList) {
		this.ticketsList = ticketsList;
	}

	public List<TicketModel> getTicketsModelList() {
		return ticketsModelList;
	}

	public void setTicketsModelList(List<TicketModel> ticketsModelList) {
		this.ticketsModelList = ticketsModelList;
	}

	public boolean isTicketsLess() {
		return ticketsLess;
	}

	public void setTicketsLess(boolean ticketsLess) {
		this.ticketsLess = ticketsLess;
	}

	public void checkUser(int userId) {
		if (this.userId != userId) {

			bookingsList = new ArrayList<>();
			bookingsHistory = new ArrayList<>();
			List<Bookings> t = bookingsService.getUserBookingsByUserId(userId,
					Bookings.BOOKED);
			if (t != null && !t.isEmpty()) {
				bookingsList.addAll(t);
			}
			t = bookingsService.getUserBookingsByUserId(userId, Bookings.PAID);
			if (t != null && !t.isEmpty()) {
				bookingsHistory.addAll(t);
			}
			this.userId = userId;
			if (booking != null) {
				bookingsList.add(booking);
			}
		}
		booking = null;
	}

	public String addToCart(FlightsOrder flightOrder, boolean isLoggedIn,
			Users user) {

		if (!isLoggedIn || user == null) {
			return "registration";
		}

		Flights f = flightsService.findById(flightOrder.getFlight().getId());

		if (flightOrder.getAmountOfTickets() > f.getAmountOfTickets()) {
			return "#";
		}

		booking = new Bookings();
		booking.setAmountOfTickets(flightOrder.getAmountOfTickets());
		booking.setBookingStatus(Bookings.BOOKED);
		booking.setDateOfBooking(Timestamp.valueOf(LocalDateTime.now()));
		booking.setFlight(f);
		booking.setUser(user);

		bookingsList.add(booking);
		return "cart";
	}

	public String sum(Integer amount, Double pricePerOne) {
		Double sum = amount * pricePerOne;
		return sum.toString();
	}

	public String getTotal() {
		Double result = 0.0;
		for (Bookings b : bookingsList) {
			result += b.getAmountOfTickets() * b.getFlight().getPrice();
		}
		return result.toString();
	}

	public String removeFromCart(Bookings booking) {
		bookingsList.remove(booking);
		Bookings b = bookingsService.findById(booking.getId());
		if (b != null) {
			bookingsService.changeBookingStatus(booking, Bookings.DECLINED);
			flightsService.changeAmountOfTickets(booking.getFlight(),
					booking.getAmountOfTickets());
		}
		return "cart";
	}

	public String edit(Bookings booking) {
		return "";
	}

	public String acceptBooking(Bookings booking) {
		ticketsList = ticketsService.getTicketsByBooking(booking);
		if (ticketsList == null) {
			ticketsList = new ArrayList<Tickets>();
			for (int i = 0; i < booking.getAmountOfTickets(); i++) {
				ticketsList.add(new Tickets());
			}
		}
		ticketsModelList = convertTicketsToModel(ticketsList);
		acceptedBooking = booking;
		return "acceptbooking";
	}

	public String orderTickets(Users user) {
		ticketsList = convertModelToTickets(ticketsModelList);
		Bookings b = bookingsService.edit(acceptedBooking);
		for (int i = 0; i < ticketsList.size(); i++) {
			ticketsList.get(i).setBooking(b);
		}
		ticketsService.saveTicketsToBooking(ticketsList);
		checkUser(-1);
		return "cart";
	}

	private List<TicketModel> convertTicketsToModel(List<Tickets> ticketsList) {
		List<TicketModel> tl = new ArrayList<>();
		for (Tickets t : ticketsList) {
			TicketModel tm = new TicketModel();
			tm.setId(t.getId());
			tm.setDateOfBirth(t.getDateOfBirth());
			tm.setFirstName(t.getFirstName());
			tm.setLastName(t.getLastName());
			tm.setPassportNumber(t.getPassportNumber());
			tm.setPassportSerialNumber(t.getPassportSerialNumber());
			tm.setPatronimic(t.getPatronimic());
			tl.add(tm);
		}
		return tl;
	}

	private List<Tickets> convertModelToTickets(
			List<TicketModel> ticketsModelList) {
		List<Tickets> tl = new ArrayList<>();
		for (TicketModel t : ticketsModelList) {
			Tickets tm = new Tickets();
			tm.setId(t.getId());
			tm.setDateOfBirth(t.getDateOfBirth());
			tm.setFirstName(t.getFirstName());
			tm.setLastName(t.getLastName());
			tm.setPassportNumber(t.getPassportNumber());
			tm.setPassportSerialNumber(t.getPassportSerialNumber());
			tm.setPatronimic(t.getPatronimic());
			tl.add(tm);
		}
		return tl;
	}

	public void checkIfNumberOfTicketsLessThenBooked(FlightsOrder f) {
		if (f.getAmountOfTickets() > f.getFlight().getAmountOfTickets()) {
			ticketsLess = true;
		} else {
			ticketsLess = false;
		}
	}
}
