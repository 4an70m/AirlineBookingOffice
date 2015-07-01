package com.bionic.bookoffice.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.service.BookingsService;

@Named
@Scope("session")
public class BookingsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Bookings> bookingsList;
	private List<Bookings> bookingsListHelper;
	private int sort;
	private Date date;

	@Inject
	private BookingsService bookingsService;

	public BookingsBean() {
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Bookings> getBookingsList() {

		if (date != null) {
			bookingsList = new ArrayList<>();
			for (Bookings b : bookingsListHelper) {
				if (b.getDateOfBooking().getTime() == date.getTime()) {
					bookingsList.add(b);
				}
			}
		}
		else{
			bookingsList = new ArrayList<>();
			bookingsList.addAll(bookingsListHelper);
		}

		switch (sort) {
		case 1: {
			bookingsList.sort(new Comparator<Bookings>() {
				@Override
				public int compare(Bookings o1, Bookings o2) {
					return o1.getUser().getLogin()
							.compareTo(o2.getUser().getLogin());
				}
			});
			break;
		}

		case 2: {
			bookingsList.sort(new Comparator<Bookings>() {
				@Override
				public int compare(Bookings o1, Bookings o2) {
					return o2.getUser().getLogin()
							.compareTo(o1.getUser().getLogin());
				}
			});
			break;
		}
		}

		return bookingsList;
	}

	public void setBookingsList(List<Bookings> bookingsList) {
		this.bookingsList = bookingsList;
	}

	public void refreshBookings() {
		bookingsList = bookingsService.getListOfUnpaidBookings();
		bookingsListHelper = new ArrayList<>();
		if (!bookingsList.isEmpty()) {
			bookingsListHelper.addAll(bookingsList);
		}
	}

	public String setPaid(Bookings b) {
		bookingsService.setBookedTicketPaid(b);
		return "#";
	}

	public String sum(Integer amount, Double pricePerOne) {
		Double sum = amount * pricePerOne;
		return sum.toString();
	}

}
