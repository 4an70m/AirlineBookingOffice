package com.bionic.bookoffice.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.service.FlightsService;

@Named
@Scope("session")
public class SearchBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fromPlace;
	private String toPlace;
	private Date fromDate;
	private Date toDate;
	private String preferedAirlines;
	private int maximumPrice;
	private int amountOfTickets;
	private Flights flight;
	private boolean searchPerformed = false;
	private List<FlightsOrder> flightsListHelper = null;
	private List<FlightsOrder> flightsList = null;
	private int sort = 1;
	private int[] timeOfDay;

	public int[] getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(int[] timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void refreshFlight() {
		flight = flightsService.findById(1);
	}

	public boolean isSearchPerformed() {
		return searchPerformed;
	}

	public void setSearchPerformed(boolean searchPerformed) {
		this.searchPerformed = searchPerformed;
	}

	public Flights getFlight() {
		return flight;
	}

	public void setFlight(Flights flight) {
		this.flight = flight;
	}

	@Inject
	private FlightsService flightsService;

	public SearchBean() {
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getPreferedAirlines() {
		return preferedAirlines;
	}

	public void setPreferedAirlines(String preferedAirlines) {
		this.preferedAirlines = preferedAirlines;
	}

	public int getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(int maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public void setAmountOfTickets(int amountOfTickets) {
		this.amountOfTickets = amountOfTickets;
	}

	public List<FlightsOrder> getFlightsList() {

		if (timeOfDay != null) {
			flightsList = new ArrayList<>();
			if (timeOfDay.length == 0) {
				flightsList.addAll(flightsListHelper);
			}
			for (int i : timeOfDay) {
				switch (i) {
				case 1: {
					for (FlightsOrder t : flightsListHelper) {
						if (t.getFlight().getDepartureDate().getHours() > 4
								&& t.getFlight().getDepartureDate().getHours() < 10) {
							flightsList.add(t);
						}

					}
					break;
				}
				case 2: {

					for (FlightsOrder t : flightsListHelper) {
						if (t.getFlight().getDepartureDate().getHours() >= 10
								&& t.getFlight().getDepartureDate().getHours() < 18) {
							flightsList.add(t);
						}

					}

					break;
				}
				case 3: {

					for (FlightsOrder t : flightsListHelper) {
						if ((t.getFlight().getDepartureDate().getHours() >= 18 && t
								.getFlight().getDepartureDate().getHours() <= 23)
								|| (t.getFlight().getDepartureDate().getHours() >= 0 && t
										.getFlight().getDepartureDate()
										.getHours() <= 4)) {
							flightsList.add(t);
						}
					}
					break;
				}

				}
				// endswitch
			}
			// endfor
		}

		switch (sort) {
		case 1: {
			Collections.sort(flightsList, new Comparator<FlightsOrder>() {

				@Override
				public int compare(FlightsOrder o1, FlightsOrder o2) {
					if (o1.getFlight().getPrice() > o2.getFlight().getPrice())
						return 1;
					if (o1.getFlight().getPrice() < o2.getFlight().getPrice())
						return -1;
					return 0;
				}

			});
			break;
		}
		case 2: {

			Collections.sort(flightsList, new Comparator<FlightsOrder>() {
				@Override
				public int compare(FlightsOrder o1, FlightsOrder o2) {
					if (o1.getFlight().getPrice() < o2.getFlight().getPrice())
						return 1;
					if (o1.getFlight().getPrice() > o2.getFlight().getPrice())
						return -1;
					return 0;
				}
			});

			break;
		}
		}
		
		return flightsList;
	}

	public void setFlightsList(List<FlightsOrder> flightsList) {
		this.flightsList = flightsList;
	}

	public void refreshList() {
		flightsListHelper = new ArrayList<>();
		searchPerformed = true;
		List<Flights> flightsList = flightsService
				.findFlightsByLocalDateLocalDateCityFromCityTo(fromDate,
						toDate, fromPlace, toPlace);
		this.flightsList = new ArrayList<FlightsOrder>(flightsList.size());
		for (Flights f : flightsList) {
			this.flightsList.add(new FlightsOrder(f, 1));
			flightsListHelper.add(new FlightsOrder(f, 1));
		}
	}

	public void refreshSearch() {
		searchPerformed = false;
	}
}
