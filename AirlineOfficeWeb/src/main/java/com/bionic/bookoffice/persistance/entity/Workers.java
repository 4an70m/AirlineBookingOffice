package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Workers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String telNo;
	private int rights;
	private java.sql.Date dateOfReg;
	private short isActive;

	@Transient
	public static final transient int BUSINESS_ANALYST = 0;
	@Transient
	public static final transient int MANAGER = 1;
	@Transient
	public static final transient int SECURITY_OFFICER = 2;
	@Transient
	public static final transient int ACCOUNTANT = 3;
	@Transient
	public static final transient short ACTIVE = 0;
	@Transient
	public static final transient short NOT_ACTIVE = 1;

	public Workers() {
	}

	public Workers(long id, String login, String password, String name,
			String surname, String telNo, int rights, Date dateOfReg,
			short isActive) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.telNo = telNo;
		this.rights = rights;
		this.dateOfReg = dateOfReg;
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getRights() {
		return rights;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public java.sql.Date getDateOfReg() {
		return dateOfReg;
	}

	public void setDateOfReg(java.sql.Date dateOfReg) {
		this.dateOfReg = dateOfReg;
	}

	public short getIsActive() {
		return isActive;
	}

	public void setIsActive(short isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	@Override
	public String toString() {
		return "Workers [id=" + id + ", login=" + login + ", password="
				+ password + ", name=" + name + ", surname=" + surname
				+ ", telNo=" + telNo + ", rights=" + rights + ", dateOfReg="
				+ dateOfReg + ", isActive=" + isActive + "]";
	}

}