package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Users implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String login;
	private String password;
	private java.sql.Date dateOfReg;
	private short isActive;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<Bookings> bookings;
	
	@Transient
	public static final transient short ACTIVE = 0;
	@Transient
	public static final transient short NOT_ACTIVE = 1;

	public Users() {
	}

	public Users(long id, String login, String password,
			Date dateOfReg, short isActive, Collection<Bookings> bookings) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.dateOfReg = dateOfReg;
		this.isActive = isActive;
		this.bookings = bookings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.sql.Date getDateOfReg() {
		return dateOfReg;
	}

	public void setDateOfReg(java.sql.Date dateOfReg) {
		this.dateOfReg = dateOfReg;
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
		return "Users [id=" + id + ", login="
				+ login + ", password=" + password + ", dateOfReg=" + dateOfReg
				+ ", isActive=" + isActive + "]";
	}

}
