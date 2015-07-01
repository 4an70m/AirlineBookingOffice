package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.AircraftsDao;
import com.bionic.bookoffice.persistance.entity.Aircrafts;

@Named
public class AircraftsServiceImpl implements AircraftsService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AircraftsDao aircraftsDao;

	@Override
	public Aircrafts findById(long id) {
		return aircraftsDao.findById(id);
	}

	@Transactional
	@Override
	public void save(Aircrafts aircrafts) {
		aircraftsDao.save(aircrafts);

	}

	@Transactional
	@Override
	public void delete(long id) {
		aircraftsDao.delete(id);
	}

	@Transactional
	@Override
	public void edit(Aircrafts aircrafts) {
		aircraftsDao.edit(aircrafts);
	}

	@Override
	public List<Aircrafts> browseAircraftsByNamePattern(String namePattern) {
		return aircraftsDao.browseAircraftsByNamePattern(namePattern);
	}

	@Override
	public List<Aircrafts> getAllAircrafts() {
		return aircraftsDao.getAllAircrafts();
	}

}
