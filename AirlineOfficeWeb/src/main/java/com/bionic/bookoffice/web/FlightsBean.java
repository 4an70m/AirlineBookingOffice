package com.bionic.bookoffice.web;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Aircrafts;
import com.bionic.bookoffice.persistance.entity.ArrivalsDepartures;
import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.service.AircraftsService;
import com.bionic.bookoffice.persistance.service.ArrivalsDeparturesService;
import com.bionic.bookoffice.persistance.service.BookingsService;
import com.bionic.bookoffice.persistance.service.FlightsService;

@Named
@Scope("session")
public class FlightsBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Flights flight;
	private Set<ArrivalsDepartures> arrDeppSet;
	private Set<Aircrafts> aircraftsSet;
	private int placeFromId;
	private int placeToId;
	private Date departureDate;
	private int aircraftId;
	private int flightTime;
	private double price;
	private int amountOfTickets;
	private short activity;
	private boolean closingFlight = false;
	private int closingBookings;
	private boolean isTicketsLess = false;
	private int deltaTickets;
	private Map<Short, String> activityMap;

	@Inject
	private FlightsService flightsService;
	@Inject
	private ArrivalsDeparturesService arrDepService;
	@Inject
	private AircraftsService aircraftsService;
	@Inject
	private BookingsService bookingsService;

	public FlightsBean() {
		activityMap = new LinkedHashMap<>();
		activityMap.put(Flights.ACTIVE, "Active");
		activityMap.put(Flights.NOT_ACTIVE, "Not Active");
	}

	public void init() {
		arrDeppSet = new TreeSet<>();
		aircraftsSet = new TreeSet<>();
		arrDeppSet.addAll(arrDepService.getAllArrivalDepartures());
		aircraftsSet.addAll(aircraftsService.getAllAircrafts());
	}

	public Flights getFlight() {
		return flight;
	}

	public void setFlight(Flights flight) {
		this.flight = flight;
	}

	public Set<ArrivalsDepartures> getArrDeppSet() {
		return arrDeppSet;
	}

	public void setArrDeppSet(Set<ArrivalsDepartures> arrDeppSet) {
		this.arrDeppSet = arrDeppSet;
	}

	public Set<Aircrafts> getAircraftsSet() {
		return aircraftsSet;
	}

	public void setAircraftsSet(Set<Aircrafts> aircraftsSet) {
		this.aircraftsSet = aircraftsSet;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public int getPlaceFromId() {
		return placeFromId;
	}

	public void setPlaceFromId(int placeFromId) {
		this.placeFromId = placeFromId;
	}

	public int getPlaceToId() {
		return placeToId;
	}

	public void setPlaceToId(int placeToId) {
		this.placeToId = placeToId;
	}

	public int getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(int aircraftId) {
		this.aircraftId = aircraftId;
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public void setAmountOfTickets(int amountOfTickets) {
		this.amountOfTickets = amountOfTickets;
	}

	public boolean isClosingFlight() {
		return closingFlight;
	}

	public void setClosingFlight(boolean closingFlight) {
		this.closingFlight = closingFlight;
	}

	public int getClosingBookings() {
		return closingBookings;
	}

	public void setClosingBookings(int closingBookings) {
		this.closingBookings = closingBookings;
	}

	public Map<Short, String> getActivityMap() {
		return activityMap;
	}

	public void setActivityMap(Map<Short, String> activityMap) {
		this.activityMap = activityMap;
	}

	public boolean isTicketsLess() {
		return isTicketsLess;
	}

	public void setTicketsLess(boolean isTicketsLess) {
		this.isTicketsLess = isTicketsLess;
	}

	public int getDeltaTickets() {
		return deltaTickets;
	}

	public void setDeltaTickets(int deltaTickets) {
		this.deltaTickets = deltaTickets;
	}

	public String editFlight(Flights flight) {
		this.flight = flight;
		departureDate = flight.getDepartureDate();
		activity = flight.getIsActive();
		flightsService.edit(flight);
		return "editflight";
	}

	public short getActivity() {
		return activity;
	}

	public void setActivity(short activity) {
		this.activity = activity;
	}

	public String addFlight() {
		Aircrafts aircraft = aircraftsService.findById(aircraftId);
		ArrivalsDepartures placeTo = arrDepService.findById(placeToId);
		ArrivalsDepartures placeFrom = arrDepService.findById(placeFromId);

		Flights flight = new Flights();
		flight.setAircraft(aircraft);
		flight.setAmountOfTickets(amountOfTickets);
		flight.setArrival(placeTo);
		flight.setDeparture(placeFrom);
		flight.setDepartureDate(departureDate);
		flight.setFlightTime(flightTime);
		flight.setIsActive(Flights.ACTIVE);
		flight.setPrice(price);
		flightsService.save(flight);

		aircraft = null;
		amountOfTickets = 0;
		placeTo = null;
		placeFromId = 0;
		placeFrom = null;
		placeToId = 0;
		departureDate = null;
		flightTime = 0;
		price = 0;
		return "index";
	}

	public void checkIfActivityClosesBooking() {
		if (flight.getIsActive() == Flights.ACTIVE
				&& activity == Flights.NOT_ACTIVE) {
			closingFlight = true;
			List<Bookings> bu = bookingsService.getActiveBookingsByFlightId(flight.getId());
			closingBookings = bu.size();
			if (closingBookings == 0) {
				closingFlight = false;
			}
			return;
		}
		closingFlight = false;
	}

	public String saveEditing() {
		flight.setIsActive(activity);
		flight.setDepartureDate(departureDate);
		if (closingFlight == true) {
			bookingsService.closeBookingsByFlightId(flight.getId());
		}
		if (isTicketsLess == true) {
			bookingsService.closeSomeBookingsByFlightId(flight.getId(),
					deltaTickets);
		}
		flightsService.edit(flight);
		departureDate = null;
		closingFlight = false;
		isTicketsLess = false;
		return "airlines";
	}

	public void checkIfNumberOfTicketsLessThenBooked(){
		List<Bookings> bu = bookingsService.getActiveBookingsByFlightId(flight.getId());
		
		deltaTickets = bu.size();
		if (deltaTickets > flight.getAmountOfTickets()){
			deltaTickets = Math.abs(deltaTickets - flight.getAmountOfTickets());
			isTicketsLess = true;
			return;
		}
		isTicketsLess = false;
	}
	
	public void closeFlihgt(Flights flight){
		List<Bookings> bu = bookingsService.getActiveBookingsByFlightId(flight.getId());
		closingBookings = bu.size();
		if (closingBookings == 0) {
			closingFlight = false;
			return;
		}
		closingFlight = true;
	}
	
	/*
	public String acceptClosing(Flights flight){
		flight.setIsActive(Flights.NOT_ACTIVE);
		if (closingFlight == true) {
			bookingsService.closeBookingsByFlightId(flight.getId());
		}
		flightsService.edit(flight);
		closingFlight = false;
		closingBookings = 0;
		return "#";
	}
	*/
}
