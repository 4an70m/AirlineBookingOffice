package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ArrivalsDepartures implements Serializable, Comparable<ArrivalsDepartures> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String country;
	private String city;
	private String airport;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "departure")
	private Collection<Flights> departureFlights;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arrival")
	private Collection<Flights> arrivalFlights;

	public ArrivalsDepartures() {
	}

	public ArrivalsDepartures(long id, String country, String city,
			String airport) {
		super();
		this.id = id;
		this.country = country;
		this.city = city;
		this.airport = airport;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public Collection<Flights> getDepartureFlights() {
		return departureFlights;
	}

	public void setDepartureFlights(Collection<Flights> departureFlights) {
		this.departureFlights = departureFlights;
	}

	public Collection<Flights> getArrivalFlights() {
		return arrivalFlights;
	}

	public void setArrivalFlights(Collection<Flights> arrivalFlights) {
		this.arrivalFlights = arrivalFlights;
	}

	@Override
	public String toString() {
		return "ArrivalsDepartures [id=" + id + ", country=" + country
				+ ", city=" + city + ", airport=" + airport + "]";
	}

	@Override
	public int compareTo(ArrivalsDepartures o) {
		return this.country.compareTo(o.country);
	}

}
