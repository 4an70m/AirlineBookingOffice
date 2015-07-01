package com.bionic.bookoffice.persistance.dao;

import java.util.List;

import com.bionic.bookoffice.persistance.entity.Aircrafts;

public interface AircraftsDao {

	public Aircrafts findById(long id);

	public void save(Aircrafts aircrafts);

	public void delete(long id);

	public void edit(Aircrafts aircrafts);

	public List<Aircrafts> browseAircraftsByNamePattern(String namePattern);

	public List<Aircrafts> getAllAircrafts();

}
