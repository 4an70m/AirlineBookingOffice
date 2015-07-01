package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Flights implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aircraftId")
	private Aircrafts aircraft;
	private java.sql.Timestamp departureDate;
	private int flightTime;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departureId")
	private ArrivalsDepartures departure;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrivalId")
	private ArrivalsDepartures arrival;
	private int amountOfTickets;
	private double price;
	private short isActive;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "flight")
	private Collection<Bookings> bookings;
	
	@Transient
	public static transient short ACTIVE = 0;
	@Transient
	public static transient short NOT_ACTIVE = 1;

	public Flights() {
	}

	public Flights(long id, Aircrafts aircraft, Timestamp departureDate,
			int flightTime, ArrivalsDepartures departure,
			ArrivalsDepartures arrival, int amountOfTickets, double price,
			short isActive, Collection<Bookings> bookings) {
		super();
		this.id = id;
		this.aircraft = aircraft;
		this.departureDate = departureDate;
		this.flightTime = flightTime;
		this.departure = departure;
		this.arrival = arrival;
		this.amountOfTickets = amountOfTickets;
		this.price = price;
		this.isActive = isActive;
		this.bookings = bookings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Aircrafts getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircrafts aircraft) {
		this.aircraft = aircraft;
	}

	public java.sql.Timestamp getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(java.sql.Timestamp departureDate) {
		this.departureDate = departureDate;
	}
	
	public void setDepartureDate(java.util.Date departureDate) {
		this.departureDate = new Timestamp(departureDate.getTime());
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public ArrivalsDepartures getDeparture() {
		return departure;
	}

	public void setDeparture(ArrivalsDepartures departure) {
		this.departure = departure;
	}

	public ArrivalsDepartures getArrival() {
		return arrival;
	}

	public void setArrival(ArrivalsDepartures arrival) {
		this.arrival = arrival;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public void setAmountOfTickets(int amountOfTickets) {
		this.amountOfTickets = amountOfTickets;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public short getIsActive() {
		return isActive;
	}

	public void setIsActive(short isActive) {
		this.isActive = isActive;
	}

	public Collection<Bookings> getBookings() {
		return bookings;
	}

	public void setBookings(Collection<Bookings> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "Flights [id=" + id + ", aircraft=" + aircraft
				+ ", departureDate=" + departureDate + ", flightTime="
				+ flightTime + ", departure=" + departure + ", arrival="
				+ arrival + ", amountOfTickets=" + amountOfTickets + ", price="
				+ price + ", isActive=" + isActive + ", bookings=" + bookings
				+ "]";
	}

}
