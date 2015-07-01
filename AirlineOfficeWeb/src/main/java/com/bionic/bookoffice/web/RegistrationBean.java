package com.bionic.bookoffice.web;

import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Users;
import com.bionic.bookoffice.persistance.service.UsersService;

@Named
@Scope("request")
public class RegistrationBean {

	private String login;
	private String password;

	@Inject
	private UsersService usersService;

	@ManagedProperty(value = "#{loginBean.user}")
	private Users user;

	@ManagedProperty(value = "#{loginBean.loggedIn}")
	private boolean isLoggedIn;

	public RegistrationBean() {
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String reg() {
		if ((user = usersService.registrateNewUser(login, password)) != null) {
			isLoggedIn = true;
			return "index";
		}
		return "registration";
	}
}
