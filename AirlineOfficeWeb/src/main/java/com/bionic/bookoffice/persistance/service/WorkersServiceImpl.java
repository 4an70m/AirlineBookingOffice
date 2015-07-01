package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.WorkersDao;
import com.bionic.bookoffice.persistance.entity.Workers;

@Named
public class WorkersServiceImpl implements WorkersService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private WorkersDao workerDao;

	@Override
	public Workers findById(long id) {
		return workerDao.findById(id);
	}

	@Transactional
	@Override
	public void save(Workers worker) {
		workerDao.save(worker);
	}

	@Transactional
	@Override
	public void delete(long id) {
		workerDao.delete(id);
	}

	@Transactional
	@Override
	public void edit(Workers worker) {
		workerDao.edit(worker);
	}

	@Override
	public List<Workers> browseActiveWorkers() {
		return workerDao.browseActiveWorkers();
	}

	@Override
	public List<Workers> findWorkersByNameOrSurname(String NameSurname) {
		return workerDao.findWorkersByNameOrSurname(NameSurname);
	}
	
	@Transactional
	@Override
	public void deactivateAccount(Workers worker) {
		workerDao.deactivateWorker(worker);
	}
	
	@Transactional
	@Override
	public void reactivateAccount(Workers worker) {
		workerDao.reactivateAccount(worker);	
	}
	
	@Override
	public Workers findWorkersByLoginPassword(String login, String password){
		return workerDao.findWorkersByLoginPassword(login, password);
	}

	@Override
	public List<Workers> browseAllWorkers() {
		return workerDao.browseAllWorkers();
	}
}
