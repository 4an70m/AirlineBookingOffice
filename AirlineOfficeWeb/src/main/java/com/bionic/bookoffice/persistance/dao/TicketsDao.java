package com.bionic.bookoffice.persistance.dao;

import java.util.List;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.Tickets;


public interface TicketsDao {

	public Tickets findById(long id);

	public void save(Tickets tickets);

	public void delete(long id);
	
	public void edit(Tickets tickets);

	public List<Tickets> getTicketsByBooking(Bookings b);
	
	public void saveTicketsToBooking(List<Tickets> ticketsList);

}
