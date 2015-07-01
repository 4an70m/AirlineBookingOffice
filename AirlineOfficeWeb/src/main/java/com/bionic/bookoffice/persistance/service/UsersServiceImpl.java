package com.bionic.bookoffice.persistance.service;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.bookoffice.persistance.dao.UsersDao;
import com.bionic.bookoffice.persistance.entity.Users;

@Named
public class UsersServiceImpl implements UsersService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsersDao usersDao;

	@Override
	public Users findById(long id) {
		return usersDao.findById(id);
	}

	@Transactional
	@Override
	public void save(Users users) {
		usersDao.save(users);
	}

	@Transactional
	@Override
	public void delete(long id) {
		usersDao.delete(id);
	}

	@Transactional
	@Override
	public void edit(Users users) {
		usersDao.edit(users);
	}

	@Transactional
	@Override
	public Users registrateNewUser(String login, String password) {
		Users u = usersDao.findUserByLoginPassword(login, password);
		if (u == null) {

			u = new Users();
			u.setDateOfReg(Date.valueOf(LocalDate.now()));
			u.setIsActive((short) 0);
			u.setLogin(login);
			u.setPassword(password);

			usersDao.save(u);
			return u;
		}
		return null;
	}

	@Override
	public Users findByLoginPassword(String login, String password) {
		return usersDao.findUserByLoginPassword(login, password);
	}
}
