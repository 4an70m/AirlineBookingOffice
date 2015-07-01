package com.bionic.bookoffice.persistance.service;

import java.util.List;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.Tickets;

public interface TicketsService {

	public Tickets findById(long id);

	public void save(Tickets tickets);

	public void delete(long id);

	public void edit(Tickets tickets);

	public List<Tickets> getTicketsByBooking(Bookings booking);

	public void saveTicketsToBooking(List<Tickets> ticketsList);
}
