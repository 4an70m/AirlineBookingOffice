package com.bionic.bookoffice.persistance.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChartModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private String subtitleText;
	
	//x-axis values
	private List<String> xAxis;
	
	private String yAxisTitle;
	private int plotLinesValue;
	private int plotLinesWidth;
	private String color;
	
	private String tooltipSuffix;
	
	private String legendLayout;
	private String legendAlign;
	private String legendvAlign;
	private int legendBorderWidth;
	
	//series
	private Map<String, List<Double>> series;
	
	public ChartModel() {
		title = "a";
		subtitleText = "a";
		xAxis = new ArrayList<>();
		xAxis.add("a");
		yAxisTitle = "a";
		plotLinesValue = 0;
		plotLinesWidth = 1;
		color = "#808080";
		tooltipSuffix = "a";
		legendLayout = "vertical";
		legendAlign = "right";
		legendvAlign = "middle";
		legendBorderWidth = 0;
		series = new LinkedHashMap<>();
		List<Double> t = new ArrayList<>();
		t.add(1d);
		series.put("a", t);
	}

	public String getTitle() {
		return "'" + title + "'";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitleText() {
		return "'" + subtitleText + "'";
	}

	public void setSubtitleText(String subtitleText) {
		this.subtitleText = subtitleText;
	}

	public String getxAxis() {
		if(xAxis.size() == 0)
			return "";
		StringBuilder b = new StringBuilder();
		b.append("[");
		for(String t : xAxis){
			b.append("'" + t + "'");
			b.append(",");
		}
		b.deleteCharAt(b.length() - 1);
		b.append("]");
		return b.toString();
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public String getyAxisTitle() {
		return "'" + yAxisTitle + "'";
	}

	public void setyAxisTitle(String yAxisTitle) {
		this.yAxisTitle = yAxisTitle;
	}

	public int getPlotLinesValue() {
		return plotLinesValue;
	}

	public void setPlotLinesValue(int plotLinesValue) {
		this.plotLinesValue = plotLinesValue;
	}

	public int getPlotLinesWidth() {
		return plotLinesWidth;
	}

	public void setPlotLinesWidth(int plotLinesWidth) {
		this.plotLinesWidth = plotLinesWidth;
	}

	public String getColor() {
		return "'" + color + "'";
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTooltipSuffix() {
		return "'" + tooltipSuffix + "'";
	}

	public void setTooltipSuffix(String tooltipSuffix) {
		this.tooltipSuffix = tooltipSuffix;
	}

	public String getLegendLayout() {
		return "'" + legendLayout + "'";
	}

	public void setLegendLayout(String legendLayout) {
		this.legendLayout = legendLayout;
	}

	public String getLegendAlign() {
		return "'" + legendAlign + "'";
	}

	public void setLegendAlign(String legendAlign) {
		this.legendAlign = legendAlign;
	}

	public String getLegendvAlign() {
		return "'" + legendvAlign + "'";
	}

	public void setLegendvAlign(String legendvALign) {
		this.legendvAlign = legendvALign;
	}

	public int getLegendBorderWidth() {
		return legendBorderWidth;
	}

	public void setLegendBorderWidth(int legendBorderWidth) {
		this.legendBorderWidth = legendBorderWidth;
	}

	public String getSeries() {
		if(series.size() == 0)
			return "";
		
		StringBuilder b = new StringBuilder();
		b.append("[{");
		b.append("name: ");
		for(Map.Entry<String, List<Double>> entry : series.entrySet()){
			b.append("'");
			b.append(entry.getKey());
			b.append("',");
			b.append("data: ");
			b.append("[");
			b.append(formatedSeries(entry.getValue()));
			b.append("]},");
		}
		b.deleteCharAt(b.length() - 1);
		b.append("]");
		return b.toString();
	}

	private String formatedSeries(List<Double> value) {
		if(value.size() == 0)
			return "";
		StringBuilder b = new StringBuilder();
		for(Double t : value){
			b.append(t);
			b.append(",");
		}
		b.deleteCharAt(b.length() - 1);
		return b.toString();
	}

	public void setSeries(Map<String, List<Double>> series) {
		this.series = series;
	}
	
	public void addSeries(String name, List<Double> values){
		series.put(name, values);
	}
}

