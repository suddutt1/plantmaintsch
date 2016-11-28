package com.ca.maintainance.scheduler.data;

import java.util.List;
import java.util.Map;

public class MonthlyForecast {

	private Map<String,List<WeatherForecast>> forecast;
	private String[] timeSlots;
	
	
	/**
	 * 
	 */
	public MonthlyForecast() {
		super();
	}
	/**
	 * @param forecast
	 * @param timeSlots
	 */
	public MonthlyForecast(Map<String, List<WeatherForecast>> forecast,
			String[] timeSlots) {
		super();
		this.forecast = forecast;
		this.timeSlots = timeSlots;
	}
	/**
	 * @return the forecast
	 */
	public Map<String, List<WeatherForecast>> getForecast() {
		return forecast;
	}
	/**
	 * @param forecast the forecast to set
	 */
	public void setForecast(Map<String, List<WeatherForecast>> forecast) {
		this.forecast = forecast;
	}
	/**
	 * @return the timeSlots
	 */
	public String[] getTimeSlots() {
		return timeSlots;
	}
	/**
	 * @param timeSlots the timeSlots to set
	 */
	public void setTimeSlots(String[] timeSlots) {
		this.timeSlots = timeSlots;
	}
	
	
}
