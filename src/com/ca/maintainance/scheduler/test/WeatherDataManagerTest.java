package com.ca.maintainance.scheduler.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.ca.maintainance.scheduler.data.WeatherDataManager;
import com.ca.maintainance.scheduler.data.WeatherDataService;
import com.ca.maintainance.scheduler.data.WeatherForecast;

public class WeatherDataManagerTest {

	@Test
	public void testWeatherManager() {
		WeatherDataService dataService = WeatherDataManager.getServiceInstance();
		assertNotNull(dataService);
		assertEquals(true,dataService.loadWeatherData());
		List<WeatherForecast> shiftForeCast = dataService.getWeatherForecast("2017.01.22", 0, 7, 0.0, 0.0);
		assertNotNull(shiftForeCast);
		System.out.println(shiftForeCast);
	}

}
