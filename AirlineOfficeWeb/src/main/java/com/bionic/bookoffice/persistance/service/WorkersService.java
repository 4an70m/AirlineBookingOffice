package com.bionic.bookoffice.persistance.service;

import java.util.List;

import com.bionic.bookoffice.persistance.entity.Workers;

public interface WorkersService {

	public Workers findById(long id);

	public void save(Workers worker);

	public void delete(long id);

	public void edit(Workers worker);

	public List<Workers> browseActiveWorkers();
	
	public List<Workers> findWorkersByNameOrSurname(String NameSurname);
	
	public void deactivateAccount(Workers worker);
	
	public void reactivateAccount(Workers worker);
	
	public Workers findWorkersByLoginPassword(String login, String password);

	public List<Workers> browseAllWorkers();
}
