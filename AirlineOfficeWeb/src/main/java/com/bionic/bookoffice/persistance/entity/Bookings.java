package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Bookings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Users user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flightId")
	private Flights flight;
	private java.sql.Timestamp dateOfBooking;
	private int amountOfTickets;
	// 0 - booked
	// 1 - declined
	// 2 - paid
	private short bookingStatus;
	
	@Transient
	public static final transient short BOOKED = 0;
	@Transient
	public static final transient short DECLINED = 1;
	@Transient
	public static final transient short PAID = 2;

	public Bookings() {
	}

	public Bookings(long id, Users user, Flights flight,
			Timestamp dateOfBooking, int amountOfTickets, short bookingStatus) {
		super();
		this.id = id;
		this.user = user;
		this.flight = flight;
		this.dateOfBooking = dateOfBooking;
		this.amountOfTickets = amountOfTickets;
		this.bookingStatus = bookingStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Flights getFlight() {
		return flight;
	}

	public void setFlight(Flights flight) {
		this.flight = flight;
	}

	public java.sql.Timestamp getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(java.sql.Timestamp dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public void setAmountOfTickets(int amountOfTickets) {
		this.amountOfTickets = amountOfTickets;
	}

	public short getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(short bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	@Override
	public String toString() {
		return "Bookings [id=" + id + ", user=" + user + ", flight=" + flight
				+ ", dateOfBooking=" + dateOfBooking + ", amountOfTickets="
				+ amountOfTickets + ", bookingStatus=" + bookingStatus + "]";
	}

}
