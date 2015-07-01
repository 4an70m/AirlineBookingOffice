package com.bionic.bookoffice.persistance.service;

import com.bionic.bookoffice.persistance.entity.Users;

public interface UsersService {

	public Users findById(long id);

	public void save(Users users);

	public void delete(long id);
	
	public void edit(Users users);
	
	public Users registrateNewUser(String login, String password);
	
	public Users findByLoginPassword(String login, String password);

}
