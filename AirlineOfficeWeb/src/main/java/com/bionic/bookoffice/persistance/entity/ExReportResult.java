package com.bionic.bookoffice.persistance.entity;

import java.io.Serializable;

public class ExReportResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrivalsDepartures arrivalsDepartures;
	private int amountOfTickets;

	public ExReportResult() {
	}

	public ExReportResult(ArrivalsDepartures arrivalsDepartures,
			int amountOfTickets) {
		super();
		this.arrivalsDepartures = arrivalsDepartures;
		this.amountOfTickets = amountOfTickets;
	}

	public ExReportResult(ArrivalsDepartures arrivalsDepartures,
			long amountOfTickets) {
		super();
		this.arrivalsDepartures = arrivalsDepartures;
		this.amountOfTickets = (int) amountOfTickets;
	}

	public ArrivalsDepartures getArrivalsDepartures() {
		return arrivalsDepartures;
	}

	public void setArrivalsDepartures(ArrivalsDepartures arrivalsDepartures) {
		this.arrivalsDepartures = arrivalsDepartures;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public void setAmountOfTickets(int amountOfTickets) {
		this.amountOfTickets = amountOfTickets;
	}

	public void setAmountOfTickets(long amountOfTickets) {
		this.amountOfTickets = (int) amountOfTickets;
	}

	@Override
	public String toString() {
		return "ExReportResult [arrivalsDepartures=" + arrivalsDepartures
				+ ", amountOfTickets=" + amountOfTickets + "]";
	}

}
