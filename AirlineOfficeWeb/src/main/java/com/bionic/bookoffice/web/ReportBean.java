package com.bionic.bookoffice.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.bookoffice.persistance.entity.Bookings;
import com.bionic.bookoffice.persistance.entity.ExReportResult;
import com.bionic.bookoffice.persistance.entity.SimpleReportResult;
import com.bionic.bookoffice.persistance.service.BookingsService;
import com.bionic.bookoffice.persistance.utils.ChartModel;
import com.bionic.bookoffice.persistance.utils.PieModel;
import com.bionic.bookoffice.persistance.utils.PieModel.SeriesItem;

@Named
@Scope("session")
public class ReportBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date dateFrom;
	private Date dateTo;
	private List<SimpleReportResult> reportList;
	private List<ExReportResult> exReportList;
	private List<Bookings> t;
	private int status;
	private Map<Short, String> statusMap;
	private boolean sRep = false;
	private boolean exRep = false;

	// chart model
	private ChartModel chartModel;
	private PieModel pieModel;

	@Inject
	private BookingsService bookingsService;

	private boolean reportRequested = false;
	private String type;
	private int days;
	private int totalTickets;
	private int totalSum;

	public ReportBean() {
		statusMap = new HashMap<>();
		statusMap.put(Bookings.PAID, "Paid");
		statusMap.put(Bookings.DECLINED, "Declined");
		statusMap.put(Bookings.BOOKED, "Booked");

	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<SimpleReportResult> getReportList() {
		return reportList;
	}

	public void setReportList(List<SimpleReportResult> reportList) {
		this.reportList = reportList;
	}

	public List<Bookings> getT() {
		return t;
	}

	public void setT(List<Bookings> t) {
		this.t = t;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map<Short, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Short, String> statusMap) {
		this.statusMap = statusMap;
	}

	public ChartModel getChartModel() {
		return chartModel;
	}

	public void setChartModel(ChartModel chartModel) {
		this.chartModel = chartModel;
	}

	public boolean isReportRequested() {
		return reportRequested;
	}

	public void setReportRequested(boolean reportRequested) {
		this.reportRequested = reportRequested;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	public int getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(int totalSum) {
		this.totalSum = totalSum;
	}

	public PieModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieModel pieModel) {
		this.pieModel = pieModel;
	}

	public List<ExReportResult> getExReportList() {
		return exReportList;
	}

	public void setExReportList(List<ExReportResult> exReportList) {
		this.exReportList = exReportList;
	}

	public void sRepVisit() {
		if(!sRep)
			chartModel = new ChartModel();
		sRep = true;
	}

	public void exRepVisit() {
		if(!exRep)
			pieModel = new PieModel();
		exRep = true;
	}

	public String getReport() {

		reportRequested = true;
		type = statusMap.get((short) status);
		LocalDate dateFromL = dateFrom.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateToL = dateTo.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		days = (int) ChronoUnit.DAYS.between(dateFromL, dateToL);

		totalSum = 0;
		totalTickets = 0;

		reportList = bookingsService.getSimpleReportForDate(new java.sql.Date(
				dateFrom.getTime()), new java.sql.Date(dateTo.getTime()),
				(short) status);

		chartModel.setTitle("Daily report for " + type + " tickets (in $)");
		chartModel.setSubtitleText("for dates "
				+ new SimpleDateFormat("yyyy/MM/dd").format(dateFrom) + " - "
				+ new SimpleDateFormat("yyyy/MM/dd").format(dateTo));
		List<String> xAxis = new ArrayList<String>();
		List<Double> series = new ArrayList<Double>();
		for (SimpleReportResult t : reportList) {
			xAxis.add(new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()));
			series.add(t.getSum());
			totalSum += t.getSum();
			totalTickets += t.getAmountOftickets();
		}
		chartModel.setxAxis(xAxis);
		Map<String, List<Double>> s = new LinkedHashMap<>();
		s.put("Sum", series);
		chartModel.setSeries(s);
		if (totalSum == 0) {
			reportRequested = false;
		}
		return "#";
	}

	public String getExReport() {

		reportRequested = true;
		type = statusMap.get((short) status);
		LocalDate dateFromL = dateFrom.toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dateToL = dateTo.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		days = (int) ChronoUnit.DAYS.between(dateFromL, dateToL);

		totalSum = 0;
		totalTickets = 0;

		exReportList = bookingsService.getExReportForDate(new java.sql.Date(
				dateFrom.getTime()), new java.sql.Date(dateTo.getTime()),
				(short) status);

		pieModel.setTitleText("Report for amount of " + type
				+ " tickets for destinations");
		pieModel.setSubtitleText("for dates "
				+ new SimpleDateFormat("yyyy/MM/dd").format(dateFrom) + " - "
				+ new SimpleDateFormat("yyyy/MM/dd").format(dateTo));

		List<SeriesItem> series = new ArrayList<>();
		for (ExReportResult t : exReportList) {
			SeriesItem item = pieModel.new SeriesItem();
			item.setName(t.getArrivalsDepartures().getAirport() + ", "
					+ t.getArrivalsDepartures().getCity());
			item.setY(t.getAmountOfTickets());
			series.add(item);
			totalTickets += t.getAmountOfTickets();
		}
		pieModel.setSeries(series);
		if (totalTickets == 0) {
			reportRequested = false;
		}
		return "#";
	}

	public String switchPage() {
		reportRequested = false;
		reportList = new ArrayList<>();
		exReportList = new ArrayList<>();
		days = 0;
		totalTickets = 0;
		totalSum = 0;

		if (exRep && !sRep) {
			exRep = false;
			return "statistics";
		} else {
			sRep = false;
			return "exstatistics";
		}
	}
}
