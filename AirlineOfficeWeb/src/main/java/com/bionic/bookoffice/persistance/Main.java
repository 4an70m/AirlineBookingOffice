package com.bionic.bookoffice.persistance;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.ExReportResult;
import com.bionic.bookoffice.persistance.service.AircraftsService;
import com.bionic.bookoffice.persistance.service.ArrivalsDeparturesService;
import com.bionic.bookoffice.persistance.service.BookingsService;
import com.bionic.bookoffice.persistance.service.FlightsService;
import com.bionic.bookoffice.persistance.service.UsersService;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		FlightsService serviceFlights = context.getBean(FlightsService.class);
		ArrivalsDeparturesService serviceAD = context
				.getBean(ArrivalsDeparturesService.class);
		AircraftsService serviceAircrafts = context
				.getBean(AircraftsService.class);
		BookingsService serviceBookings = context
				.getBean(BookingsService.class);
		UsersService serviceUsers = context.getBean(UsersService.class);
		
		
		List<ExReportResult> t = serviceBookings.getExReportForDate(
				Date.valueOf(LocalDate.of(2010, 1, 1)), 
				Date.valueOf(LocalDate.of(2016, 1, 1)), 
				Bookings.BOOKED);
		
		for(ExReportResult q : t){
			System.out.println(q);
		}
		
		System.out.println(serviceUsers.findById(1));
	}
}
