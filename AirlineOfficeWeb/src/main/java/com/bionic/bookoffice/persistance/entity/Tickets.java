package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tickets implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookingId")
	private Bookings booking;
	private String firstName;
	private String lastName;
	private String patronimic;
	private String passportNumber;
	private String passportSerialNumber;
	private java.sql.Date dateOfBirth;

	public Tickets() {
	}

	public Tickets(long id, Bookings booking, String firstName,
			String lastName, String patronimic, String passportNumber,
			String passportSerialNumber, Date dateOfBirth) {
		super();
		this.id = id;
		this.booking = booking;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronimic = patronimic;
		this.passportNumber = passportNumber;
		this.passportSerialNumber = passportSerialNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Bookings getBooking() {
		return booking;
	}

	public void setBooking(Bookings booking) {
		this.booking = booking;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPatronimic() {
		return patronimic;
	}

	public void setPatronimic(String patronimic) {
		this.patronimic = patronimic;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassportSerialNumber() {
		return passportSerialNumber;
	}

	public void setPassportSerialNumber(String passportSerialNumber) {
		this.passportSerialNumber = passportSerialNumber;
	}

	public java.sql.Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		System.out.println("call String Date");
		this.dateOfBirth = Date.valueOf(dateOfBirth);
	}
	
	public void setDateOfBirth(java.util.Date dateOfBirth) {
		System.out.println("call util Date");
		this.dateOfBirth = new Date(dateOfBirth.getTime());
	}
	
	public void setDateOfBirth(java.sql.Date dateOfBirth) {
		System.out.println("call sql Date");
		this.dateOfBirth = new Date(dateOfBirth.getTime());
	}


	@Override
	public String toString() {
		return "Tickets [id=" + id + ", booking=" + booking + ", firstName="
				+ firstName + ", lastName=" + lastName + ", patronimic="
				+ patronimic + ", passportNumber=" + passportNumber
				+ ", passportSerialNumber=" + passportSerialNumber
				+ ", dateOfBirth=" + dateOfBirth + "]";
	}

}