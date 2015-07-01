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
public class Aircrafts implements Serializable, Comparable<Aircrafts>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String aircraftName;
	private String ownerName;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "aircraft")
	private Collection<Flights> flights;

	public Aircrafts() {
	}

	public Aircrafts(long id, String aircraftName, String ownerName) {
		super();
		this.id = id;
		this.aircraftName = aircraftName;
		this.ownerName = ownerName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAircraftName() {
		return aircraftName;
	}

	public void setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Collection<Flights> getFlights() {
		return flights;
	}

	public void setFlights(Collection<Flights> flights) {
		this.flights = flights;
	}

	@Override
	public String toString() {
		return "Aircrafts [id=" + id + ", aircraftName=" + aircraftName
				+ ", ownerName=" + ownerName + "]";
	}

	@Override
	public int compareTo(Aircrafts o) {
		return this.ownerName.compareTo(o.ownerName);
	}

}
