package com.bionic.bookoffice.persistance.dao;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.ExReportResult;
import com.bionic.bookoffice.persistance.entity.Flights;
import com.bionic.bookoffice.persistance.entity.SimpleReportResult;
import com.bionic.bookoffice.persistance.entity.Users;

@Repository
public class BookingsDaoImpl implements BookingsDao, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public Bookings findById(long id) {
		return em.find(Bookings.class, id);
	}

	@Override
	public void save(Bookings bookings) {
		em.persist(bookings);
	}

	@Override
	public void delete(long id) {
		Bookings bookings = em.find(Bookings.class, id);
		if (bookings != null) {
			em.remove(bookings);
		}
	}

	@Override
	public Bookings edit(Bookings bookings) {
		return em.merge(bookings);
	}

	@Override
	public List<Bookings> viewBookingsByUser(Users user) {
		TypedQuery<Bookings> query = em.createQuery(
				"SELECT DISTINCT b FROM Bookings b WHERE b.userId = ?1",
				Bookings.class);
		query.setParameter(1, user.getId());
		return query.getResultList();
	}

	@Override
	public List<Bookings> getListOfUnpaidBookings() {
		return em.createQuery(
				"SELECT b FROM Bookings b WHERE b.bookingStatus = 0",
				Bookings.class).getResultList();
	}

	@Override
	public void setBookingPaid(Bookings booking) {
		Bookings b = em.find(Bookings.class, booking.getId());
		if (b != null) {
			b.setBookingStatus((short) 2);
		}
	}

	@Override
	public void unbookATicket(Bookings booking) {
		Bookings b = em.find(Bookings.class, booking.getId());
		if (b != null) {
			b.setBookingStatus((short) 1);
		}
	}

	@Override
	public List<Bookings> getUserBookingsByUser(Users user, short bookingStatus) {
		TypedQuery<Bookings> q = em
				.createQuery(
						"SELECT b FROM Bookings b WHERE b.user = ?1 AND b.bookingStatus = ?2",
						Bookings.class);
		q.setParameter(1, user);
		q.setParameter(2, bookingStatus);
		return q.getResultList();
	}

	@Override
	public void changeBookingStatus(Bookings booking, short status) {
		booking = findById(booking.getId());
		booking.setBookingStatus(status);
		edit(booking);
	}

	@Override
	public void closeBookingsByFlightId(Flights f) {
		TypedQuery<Bookings> q = em
				.createQuery(
						"UPDATE Bookings b SET b.bookingStatus = ?1 WHERE b.flight = ?2",
						Bookings.class);
		q.setParameter(1, Bookings.DECLINED);
		q.setParameter(2, f);
		q.executeUpdate();
	}

	@Override
	public void closeSomeBookingsByFlightId(Flights f, int amountOfClosing) {
		List<Bookings> bl = new ArrayList<>();
		bl.addAll(f.getBookings());
		for(int i = 0; i < amountOfClosing; i++){
			if(bl.size() < i)
				return;
			Bookings b = bl.get(i);
			if(b.getBookingStatus() != Bookings.DECLINED){
				b.setBookingStatus(Bookings.DECLINED);
				edit(b);
			}
		}
		
	}

	@Override
	public List<Bookings> getActiveBookingsByFlightId(Flights f) {
		TypedQuery<Bookings> q = em.createQuery("SELECT b FROM Bookings b WHERE b.bookingStatus <> ?1 and b.flight = ?2", Bookings.class);
		q.setParameter(1, Bookings.DECLINED);
		q.setParameter(2, f);
		return q.getResultList();
	}

	@Override
	public List<SimpleReportResult> getSimpleReportForDate(Date dateFrom, Date dateTo, short status){
		TypedQuery<SimpleReportResult> query = em
				.createQuery("SELECT new com.bionic.bookoffice.persistance.entity.SimpleReportResult"
						+ "(b.dateOfBooking, SUM(b.amountOfTickets)) "
						+ "FROM Bookings b "
						+ "WHERE b.bookingStatus = ?3 AND b.dateOfBooking > ?1 AND b.dateOfBooking  < ?2 "
						+ "GROUP BY b.dateOfBooking", SimpleReportResult.class);
		query.setParameter(1, dateFrom);
		query.setParameter(2, dateTo);
		query.setParameter(3, status);
		List<SimpleReportResult> t = query.getResultList();

		TypedQuery<Double> query2 = em.createQuery("SELECT SUM(f.price * b1.amountOfTickets) "
					+ "FROM Bookings b1 INNER JOIN Flights f ON b1.flight = f "
					+ "WHERE b1.bookingStatus = ?3 AND b1.dateOfBooking > CAST(?1 AS DATE) AND b1.dateOfBooking < CAST(?2 AS DATE)"
					+ "GROUP BY b1.dateOfBooking", Double.class);
		query2.setParameter(1, dateFrom);
		query2.setParameter(2, dateTo);
		query2.setParameter(3, status);
		List<Double> t2 = query2.getResultList();
		for(int i = 0; i < t.size(); i++){
			t.get(i).setSum(t2.get(i));
		}

		return t;
	}


	@Override
	public List<Bookings> getBookingsBetweenDates(Date valueOf, Date valueOf2) {
		TypedQuery<Bookings> q = em.createQuery("SELECT b FROM Bookings b WHERE b.dateOfBooking > ?1 AND b.dateOfBooking < ?2", Bookings.class);
		q.setParameter(1, valueOf);
		q.setParameter(2, valueOf2);
		return q.getResultList();
	}

	@Override
	public List<ExReportResult> getExReportForDate(Date dateFrom, Date dateTo,
			short status) {
		TypedQuery<ExReportResult> q = em.createQuery("Select "
				+ "new com.bionic.bookoffice.persistance.entity.ExReportResult(ad, SUM(b.amountOfTickets)) "
				+ "FROM Bookings b "
				+ "left join Flights f on b.flight = f "
				+ "left join ArrivalsDepartures ad on ad = f.arrival "
				+ "WHERE b.bookingStatus = ?3 AND ?1 < f.departureDate AND f.departureDate < ?2 "
				+ "Group by ad", ExReportResult.class);
		q.setParameter(1, dateFrom);
		q.setParameter(2, dateTo);
		q.setParameter(3, status);		
		return q.getResultList();
	}
}
