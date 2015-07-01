package com.bionic.bookoffice.web;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Users;
import com.bionic.bookoffice.persistance.entity.Workers;
import com.bionic.bookoffice.persistance.service.UsersService;
import com.bionic.bookoffice.persistance.service.WorkersService;

@Named
@Scope("session")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isLoggedIn = false;
	private int workerType = -1;
	private boolean isWorker;
	private Users user;
	private String login;
	private String password;
	private Workers worker;

	private boolean securityOfficer = false;
	private boolean analyst = false;
	private boolean manger = false;
	private boolean accountant = false;

	@Inject
	private UsersService usersService;
	@Inject
	private WorkersService workersService;

	public UserBean() {
	}

	public boolean isWorker() {
		return isWorker;
	}

	public void setWorker(boolean isWorker) {
		this.isWorker = isWorker;
	}

	public int getWorkerType() {
		return workerType;
	}

	public void setWorkerType(int workerType) {
		this.workerType = workerType;
	}

	public Workers getWorker() {
		return worker;
	}

	public void setWorker(Workers worker) {
		this.worker = worker;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSecurityOfficer() {
		return securityOfficer;
	}

	public void setSecurityOfficer(boolean securityOfficer) {
		this.securityOfficer = securityOfficer;
	}

	public boolean isAnalyst() {
		return analyst;
	}

	public void setAnalyst(boolean analyst) {
		this.analyst = analyst;
	}

	public boolean isManger() {
		return manger;
	}

	public void setManger(boolean manger) {
		this.manger = manger;
	}

	public boolean isAccountant() {
		return accountant;
	}

	public void setAccountant(boolean accountant) {
		this.accountant = accountant;
	}

	public String logIn() {
		user = usersService.findByLoginPassword(login, password);
		if (user != null) {
			isLoggedIn = true;
		} else {
			worker = workersService.findWorkersByLoginPassword(login, password);
			if (worker != null) {
				isLoggedIn = true;
				isWorker = true;
				workerType = worker.getRights();
			}
		}
		return "#";
	}

	public String logOut() {
		user = null;
		worker = null;
		workerType = -1;
		isWorker = false;
		isLoggedIn = false;

		securityOfficer = false;
		analyst = false;
		manger = false;
		accountant = false;

		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession hs = (HttpSession) fc.getExternalContext()
				.getSession(false);
		hs.invalidate();
		return "index";
	}

	public void checkIfLoggedIn(Users user, boolean isLoggedIn) {
		if (isLoggedIn && !this.isLoggedIn) {
			this.user = user;
			this.isLoggedIn = isLoggedIn;
		}
	}

	public void getRights(Workers worker) {
		if (worker == null) {
			return;
		}
		switch (worker.getRights()) {
		case Workers.MANAGER:
			manger = true;
			break;
		case Workers.SECURITY_OFFICER:
			securityOfficer = true;
			break;
		case Workers.BUSINESS_ANALYST:
			analyst = true;
			break;
		case Workers.ACCOUNTANT:
			accountant = true;
			break;
		}
	}

	public void checkLogged(int pos) throws IOException {
		if (!isLoggedIn || (isWorker == true && pos == -1)) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("error.xhtml");
			return;
		}
	
		if (isWorker == false && pos == -1) {
			return;
		} else {

			boolean error = false;
			switch (pos) {
			case Workers.MANAGER:
				error = !manger;
				break;
			case Workers.SECURITY_OFFICER:
				error = !securityOfficer;
				break;
			case Workers.BUSINESS_ANALYST:
				error = !analyst;
				break;
			case Workers.ACCOUNTANT:
				error = !accountant;
				break;
			}

			if (error) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("error.xhtml");
				return;
			}
		}
	}
}
