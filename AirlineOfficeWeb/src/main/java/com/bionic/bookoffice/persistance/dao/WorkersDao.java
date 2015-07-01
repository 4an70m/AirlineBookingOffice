package com.bionic.bookoffice.persistance.dao;

import java.util.List;

import com.bionic.bookoffice.persistance.entity.Workers;;


public interface WorkersDao {

	public Workers findById(long id);

	public void save(Workers workers);

	public void delete(long id);
	
	public void edit(Workers workers);

	public List<Workers> browseActiveWorkers();

	public void deactivateWorker(Workers worker);

	public List<Workers> findWorkersByNameOrSurname(String nameSurname);

	public void reactivateAccount(Workers worker);
	
	public Workers findWorkersByLoginPassword(String login, String password);

	public List<Workers> browseAllWorkers();

}
