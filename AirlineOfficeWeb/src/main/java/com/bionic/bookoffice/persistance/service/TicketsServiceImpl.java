package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.BookingsDao;
import com.bionic.bookoffice.persistance.dao.FlightsDao;
import com.bionic.bookoffice.persistance.dao.TicketsDao;
import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.Tickets;

@Named
public class TicketsServiceImpl implements TicketsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private TicketsDao ticketsDao;
	@Inject
	private BookingsDao bookingsDao;
	@Inject
	private FlightsDao flightsDao;

	@Override
	public Tickets findById(long id) {
		return ticketsDao.findById(id);
	}

	@Transactional
	@Override
	public void save(Tickets tickets) {
		ticketsDao.save(tickets);
	}

	@Transactional
	@Override
	public void delete(long id) {
		ticketsDao.delete(id);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void edit(Tickets tickets) {
		ticketsDao.edit(tickets);
	}

	@Override
	public List<Tickets> getTicketsByBooking(Bookings booking) {
		Bookings b = bookingsDao.findById(booking.getId());
		if(b != null){
			return ticketsDao.getTicketsByBooking(b);
		}
		return null;
	}

	@Transactional
	@Override
	public void saveTicketsToBooking(List<Tickets> ticketsList){
		flightsDao.changeAmountOfTickets(ticketsList.get(0).getBooking().getFlight(), -ticketsList.size());
		ticketsDao.saveTicketsToBooking(ticketsList);		
	}

}
