package com.ca.maintainance.scheduler.data;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataStoreManager {

	private static final Logger _LOGGER = Logger
			.getLogger(DataStoreManager.class.getName());
	private static DataStore _instance = null;
	private static boolean isDataLoaded = false;
	private static boolean isErrorOnLoad = false;
	private static final Object _mutex = new Object();

	private DataStoreManager() {
		super();
	}

	private static void init() {
		try {
			if (!isErrorOnLoad && !isDataLoaded) {
				// TODO: Externalize the following option
				synchronized (_mutex) {
					if (_instance == null) {
						_LOGGER.info("|DSMGR|Loading data store instance");
						_instance = new JSONFileDataStore();
						if (_instance.loadDataStore()) {
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
				_LOGGER.log(Level.SEVERE, "|DSMGR|Data Store load error");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.SEVERE, "|DSMGR|Unable to initialize", ex);
			isErrorOnLoad = true;
		}
	}

	public static DataStore getDataStore() {
		init();
		return _instance;
	}
}
