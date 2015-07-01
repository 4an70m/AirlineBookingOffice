package com.bionic.bookoffice.persistance.dao;

import com.bionic.bookoffice.persistance.entity.Users;


public interface UsersDao {

	public Users findById(long id);

	public void save(Users users);

	public void delete(long id);
	
	public void edit(Users users);

	public Users findUserByLoginPassword(String login, String password);

}
