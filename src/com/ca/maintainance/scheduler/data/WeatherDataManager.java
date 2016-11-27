package com.ca.maintainance.scheduler.data;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherDataManager {

	private static final Logger _LOGGER = Logger
			.getLogger(WeatherDataManager.class.getName());
	private static WeatherDataService _instance = null;
	private static boolean isDataLoaded = false;
	private static boolean isErrorOnLoad = false;
	private static final Object _mutex = new Object();

	private WeatherDataManager() {
		super();
	}

	private static void init() {
		try {
			if (!isErrorOnLoad && !isDataLoaded) {
				// TODO: Externalize the following option
				synchronized (_mutex) {
					if (_instance == null) {
						_LOGGER.info("|WDSMGR|Loading data store instance");
						_instance = new JSONFileWeatherDataService();
						if (_instance.loadWeatherData()) {
							isDataLoaded = true;
						} else {
							isErrorOnLoad = true;
							_instance = null;
						}
					}
					_mutex.notifyAll();
				}

			}
			if (isErrorOnLoad) {
				_LOGGER.log(Level.SEVERE, "|WDSMGR|Data Store load error");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE, "|WDSMGR|Unable to initialize", ex);
			isErrorOnLoad = true;
		}
	}

	public static WeatherDataService getServiceInstance() {
		init();
		return _instance;
	}
}
