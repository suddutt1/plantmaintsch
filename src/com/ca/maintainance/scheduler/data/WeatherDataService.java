package com.ca.maintainance.scheduler.data;

import java.util.List;
import java.util.Map;

public interface WeatherDataService {

	List<WeatherForecast> getWeatherForecast(String date, long startTime,
			long endTime, double latitude, double longitude);
	boolean loadWeatherData();
	boolean flushWeatherData(Map<String,String> context);
	boolean updateWeatherData(String dt,long time,WeatherElement elem,long value);
	
}
