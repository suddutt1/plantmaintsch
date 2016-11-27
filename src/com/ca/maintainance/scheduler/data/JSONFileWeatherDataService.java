package com.ca.maintainance.scheduler.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONFileWeatherDataService implements WeatherDataService {

	public static final String CTX_WEATHER_DATA_FILE = "CTX_WEATHER_DATA_FILE";
	private static final Logger _LOGGER = Logger
			.getLogger(JSONFileWeatherDataService.class.getName());
	private Map<String, List<WeatherForecast>> weatherDataMap = new HashMap<>();

	@Override
	public List<WeatherForecast> getWeatherForecast(String date,
			long startTime, long endTime, double latitude, double longitude) {
		// TODO Auto-generated method stub
		
		if(this.weatherDataMap.get(date)!=null)
		{
			List<WeatherForecast> fullDayForeCast = this.weatherDataMap.get(date);
			List<WeatherForecast> shiftForeCast = new ArrayList<>();
			for(WeatherForecast forecast: fullDayForeCast)
			{
				if(forecast.getTime()>=startTime && forecast.getTime()<=endTime )
				{
					shiftForeCast.add(forecast);
				}
			}
			return shiftForeCast;
		}
		return null;
	}
	

	@Override
	public boolean loadWeatherData() {
		boolean isLoadSuccess = false;
		try {
			Gson gson = new GsonBuilder().create();
			Type listType = new TypeToken<HashMap<String, List<WeatherForecast>>>(){}.getType();
			this.weatherDataMap = gson.fromJson(readJson("weather_forecast.json"),
					listType);
			arrageData();
			isLoadSuccess = true;
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE,
					"|WeatherJSONData| Error in loading data into store", ex);
			isLoadSuccess = false;
		}
		return isLoadSuccess;
	}
	
	private void arrageData()
	{
		Comparator<WeatherForecast> timeComp = new Comparator<WeatherForecast>() {

			@Override
			public int compare(WeatherForecast o1, WeatherForecast o2) {
				// TODO Auto-generated method stub
				return (int)(o1.getTime()-o2.getTime());
			}
		
		};
		for(List<WeatherForecast> forecastList: this.weatherDataMap.values())
		{
			Collections.sort(forecastList,timeComp);
		}
	}
	@Override
	public boolean flushWeatherData(Map<String, String> context) {
		// TODO Auto-generated method stub
		boolean isFlushSuccess = true;
		try {
			// Save equipmentMaster
			String path = context.get(CTX_WEATHER_DATA_FILE);
			String json = DataUtil.toJson(this.weatherDataMap);
			isFlushSuccess = writeToFile(path, json) & isFlushSuccess;

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING,
					"|WeatherJSONData| Unable to flush data in file...|", ex);
			isFlushSuccess = false;
		}
		return isFlushSuccess;
	}

	@Override
	public boolean updateWeatherData(String dt, long time, WeatherElement elem,
			long value) {
		boolean isSucess = true;
		int pos = -1;
		List<WeatherForecast> dayForecast = this.weatherDataMap.get(dt);
		if (dayForecast == null) {
			dayForecast = new ArrayList<>();
			this.weatherDataMap.put(dt, dayForecast);
		}
		WeatherForecast srchIndex = new WeatherForecast(dt, time, 0, 0);
		pos = dayForecast.indexOf(srchIndex);
		if (pos != -1) {
			srchIndex = dayForecast.get(pos);
		} else {
			dayForecast.add(srchIndex);
		}
		switch (elem) {
		case RAIN:
			srchIndex.setPrecpProb(value);
			break;
		case VISIBILITY:
			srchIndex.setVisibility(value);
			break;
		default:
			isSucess = false;
		}
		return isSucess;
	}

	private boolean writeToFile(String path, String json) {
		FileOutputStream fos = null;
		boolean isWriteSuccess = false;
		try {
			fos = new FileOutputStream(path);
			fos.write(json.getBytes());
			fos.flush();
			isWriteSuccess = true;
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE, "|WeatherJSONData| Unable to write to :" + path);
			isWriteSuccess = false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					_LOGGER.log(Level.SEVERE,
							"|WeatherJSONData|Unable to close file :" + path);
				}
			}
		}
		return isWriteSuccess;
	}
	private String readJson(String jsonName) throws Exception {
		String content = null;
		InputStream inputScr = JSONFileDataStore.class.getClassLoader()
				.getResourceAsStream(jsonName);
		Scanner scr = new Scanner(inputScr);
		scr.useDelimiter("\\A");
		content = (scr.hasNext() ? scr.next() : null);
		scr.close();
		inputScr.close();
		return content;
	}
}
