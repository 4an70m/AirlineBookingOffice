package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class SimpleReportResult implements Serializable{

	private static final long serialVersionUID = 1L;
	private Timestamp date;
	private int amountOftickets;
	private double sum;

	public SimpleReportResult() {
		// TODO Auto-generated constructor stub
	}

	public SimpleReportResult(Timestamp date, int amountOftickets, double sum) {
		super();
		this.date = date;
		this.amountOftickets = amountOftickets;
		this.sum = sum;
	}
	
	public SimpleReportResult(Timestamp date, long amountOftickets, double sum) {
		super();
		this.date = date;
		this.amountOftickets = (int)amountOftickets;
		this.sum = sum;
	}
	
	public SimpleReportResult(Timestamp date, long amountOftickets) {
		super();
		this.date = date;
		this.amountOftickets = (int)amountOftickets;
		this.sum = 0;
	}
	
	public SimpleReportResult(Timestamp date, double sum) {
		super();
		this.date = date;
		this.amountOftickets = 0;
		this.sum = sum;
	}



	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getAmountOftickets() {
		return amountOftickets;
	}

	public void setAmountOftickets(int amountOftickets) {
		this.amountOftickets = amountOftickets;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

}
