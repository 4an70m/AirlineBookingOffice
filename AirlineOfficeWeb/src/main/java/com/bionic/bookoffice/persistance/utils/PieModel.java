package com.bionic.bookoffice.persistance.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PieModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String chartPlotBackgroundColor;
	private String chartPlotBorderWidth;
	private String chartPlotShadow;

	private String titleText;
	private String subtitleText;
	private String tooltipPointFormat;

	private boolean plotOptionsPieAllowPointSelect;
	private String plotOptionsPieCursor;
	private boolean plotOptionsPieDataLabelsEnabled;
	private String plotOptionsPieDataLabelsFormat;
	private String plotOptionsPieDataLabelsStyle;

	private String seriesType;

	public class SeriesItem implements Serializable{

		private static final long serialVersionUID = 1L;
		private String name;
		private double y;
		private boolean sliced = false;
		private boolean selected = false;

		public SeriesItem() {
		}

		public SeriesItem(String name, double y, boolean sliced,
				boolean selected) {
			this.name = name;
			this.y = y;
			this.sliced = sliced;
			this.selected = selected;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public boolean isSliced() {
			return sliced;
		}

		public void setSliced(boolean sliced) {
			this.sliced = sliced;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append("{");
			b.append("name: '").append(name).append("', ");
			b.append("y: ").append(y).append(", ");
			b.append("sliced: ").append(sliced).append(", ");
			b.append("selected: ").append(selected).append("}");
			return b.toString();
		}

	}

	public String getSubtitleText() {
		return "'" + subtitleText + "'";
	}

	public void setSubtitleText(String subtitleText) {
		this.subtitleText = subtitleText;
	}

	private List<SeriesItem> series;

	private String seriesName;

	public PieModel() {
		chartPlotBackgroundColor = "null";
		chartPlotBorderWidth = "null";
		chartPlotShadow = "false";
		titleText = "";
		tooltipPointFormat = "'{series.name}: <b>{point.percentage:.1f}%</b>'";
		plotOptionsPieAllowPointSelect = true;
		plotOptionsPieCursor = "'pointer'";
		plotOptionsPieDataLabelsEnabled = true;
		plotOptionsPieDataLabelsFormat = "'<b>{point.name}</b>: {point.percentage:.1f} %'";
		plotOptionsPieDataLabelsStyle = "{"
				+ "color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'"
				+ "}";
		series = new ArrayList<>();
		series.add(new SeriesItem("a", 1, false, false));
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getChartPlotBackgroundColor() {
		return chartPlotBackgroundColor;
	}

	public void setChartPlotBackgroundColor(String chartPlotBackgroundColor) {
		this.chartPlotBackgroundColor = chartPlotBackgroundColor;
	}

	public String getChartPlotBorderWidth() {
		return chartPlotBorderWidth;
	}

	public void setChartPlotBorderWidth(String chartPlotBorderWidth) {
		this.chartPlotBorderWidth = chartPlotBorderWidth;
	}

	public String getChartPlotShadow() {
		return chartPlotShadow;
	}

	public void setChartPlotShadow(String chartPlotShadow) {
		this.chartPlotShadow = chartPlotShadow;
	}

	public String getTitleText() {
		return "'" + titleText + "'";
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public String getTooltipPointFormat() {
		return tooltipPointFormat;
	}

	public void setTooltipPointFormat(String tooltipPointFormat) {
		this.tooltipPointFormat = tooltipPointFormat;
	}

	public boolean isPlotOptionsPieAllowPointSelect() {
		return plotOptionsPieAllowPointSelect;
	}

	public void setPlotOptionsPieAllowPointSelect(
			boolean plotOptionsPieAllowPointSelect) {
		this.plotOptionsPieAllowPointSelect = plotOptionsPieAllowPointSelect;
	}

	public String getPlotOptionsPieCursor() {
		return plotOptionsPieCursor;
	}

	public void setPlotOptionsPieCursor(String plotOptionsPieCursor) {
		this.plotOptionsPieCursor = plotOptionsPieCursor;
	}

	public boolean isPlotOptionsPieDataLabelsEnabled() {
		return plotOptionsPieDataLabelsEnabled;
	}

	public void setPlotOptionsPieDataLabelsEnabled(
			boolean plotOptionsPieDataLabelsEnabled) {
		this.plotOptionsPieDataLabelsEnabled = plotOptionsPieDataLabelsEnabled;
	}

	public String getPlotOptionsPieDataLabelsFormat() {
		return plotOptionsPieDataLabelsFormat;
	}

	public void setPlotOptionsPieDataLabelsFormat(
			String plotOptionsPieDataLabelsFormat) {
		this.plotOptionsPieDataLabelsFormat = plotOptionsPieDataLabelsFormat;
	}

	public String getPlotOptionsPieDataLabelsStyle() {
		return plotOptionsPieDataLabelsStyle;
	}

	public void setPlotOptionsPieDataLabelsStyle(
			String plotOptionsPieDataLabelsStyle) {
		this.plotOptionsPieDataLabelsStyle = plotOptionsPieDataLabelsStyle;
	}

	public String getSeriesType() {
		return seriesType;
	}

	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	public String getSeries() {
		if (series.size() == 0)
			return "";
		StringBuilder b = new StringBuilder();
		b.append('[').append('{');
		b.append("type: 'pie', name: '");
		b.append(seriesName + "', ");
		b.append("data: [");
		for (SeriesItem t : series) {
			b.append(t.toString()).append(",");
		}
		b.deleteCharAt(b.length() - 1);
		b.append(']');
		b.append('}');
		b.append(']');
		return b.toString();
	}

	public void setSeries(List<SeriesItem> series) {
		this.series = series;
	}

	public void addSeries(SeriesItem item) {
		series.add(item);
	}

}
