package com.bionic.bookoffice.persistance.service;

import java.util.List;

import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;

public interface ArrivalsDeparturesService {

	public ArrivalsDepartures findById(long id);

	public void save(ArrivalsDepartures arrivalsDepartures);

	public void delete(long id);
	
	public void edit(ArrivalsDepartures arrivalsDepartures);
	
	public List<ArrivalsDepartures> findByCity(String city);

	public List<ArrivalsDepartures> getAllArrivalDepartures();
}
