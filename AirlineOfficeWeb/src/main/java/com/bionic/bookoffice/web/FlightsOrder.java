package com.bionic.bookoffice.web;

import java.io.Serializable;

import com.bionic.bookoffice.persistance.entity.Flights;

public class FlightsOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	private Flights flight;
	private int amountOfTickets;

	public FlightsOrder() {
		// TODO Auto-generated constructor stub
	}

	public FlightsOrder(Flights f, int i) {
		flight = f;
		amountOfTickets = i;
	}

	public Flights getFlight() {
		return flight;
	}

	public void setFlight(Flights flight) {
		this.flight = flight;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public void setAmountOfTickets(int amountOfTickets) {
		this.amountOfTickets = amountOfTickets;
	}

}
