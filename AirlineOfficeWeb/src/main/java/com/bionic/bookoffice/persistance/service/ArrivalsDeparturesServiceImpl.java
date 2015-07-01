package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.ArrivalsDeparturesDao;
import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;

@Named
public class ArrivalsDeparturesServiceImpl implements ArrivalsDeparturesService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ArrivalsDeparturesDao arrivalsDeparturesDao;

	@Override
	public ArrivalsDepartures findById(long id) {
		return arrivalsDeparturesDao.findById(id);
	}

	@Transactional
	@Override
	public void save(ArrivalsDepartures arrivalsDepartures) {
		arrivalsDeparturesDao.save(arrivalsDepartures);
	}

	@Transactional
	@Override
	public void delete(long id) {
		arrivalsDeparturesDao.delete(id);
	}

	@Transactional
	@Override
	public void edit(ArrivalsDepartures arrivalsDepartures) {
		arrivalsDeparturesDao.edit(arrivalsDepartures);
	}

	@Override
	public List<ArrivalsDepartures> findByCity(String city) {
		return arrivalsDeparturesDao.findByCity(city);
	}

	@Override
	public List<ArrivalsDepartures> getAllArrivalDepartures() {
		return arrivalsDeparturesDao.getAllArrivalsDepartures();		
	}

}
