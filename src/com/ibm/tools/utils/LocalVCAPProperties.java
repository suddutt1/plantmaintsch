package com.ibm.tools.utils;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class reads property file from WEB-INF/local_vcap.properties and
 * supplies the property values from local fall back cases. Mostly useful during
 * local testing.
 * 
 * @author SUDDUTT1
 * 
 */
public class LocalVCAPProperties {

	private static final Logger LOGGER = Logger
			.getLogger(LocalVCAPProperties.class.getName());
	private static boolean _isInitialized = false;
	private static Properties _localProperties = null;

	public static String FILE_PATH = "/WEB-INF/local_vcap.properties";

	/**
	 * Constructor made private to encourage static access only
	 */
	private LocalVCAPProperties() {
		super();
	}

	/**
	 * Initializes properties
	 * @return boolean true if load successful else false 
	 */
	private static boolean init() {
		try {
			if (_isInitialized == false) {
				InputStream stream = LocalVCAPProperties.class
						.getResourceAsStream(FILE_PATH);
				_localProperties = new Properties();
				_localProperties.load(stream);
				_isInitialized = true;
			}
		} catch (Exception ex) {
			LOGGER.log(Level.WARNING,
					"|LCOAL_VCAP_PROPERTIES|Unable to load local VCAP_PROPERTIES");
			_isInitialized = false;
		}
		return _isInitialized;
	}

	/**
	 * Returns the property value if exists in WEB-INF/local_vcap.properties
	 * 
	 * @param property String property key 
	 * @return String property value
	 */
	public static String getLocalProperty(String property) {
		if (init()) {
			return _localProperties.getProperty(property);
		}
		return null;
	}

	/**
	 * Returns the property value if exists in WEB-INF/local_vcap.properties
	 * file else the default value
	 * 
	 * @param property
	 *            Property name
	 * @param defaultValue
	 *            Default property value
	 * @return String property value
	 */
	public static String getLocalProperty(String property, String defaultValue) {
		if (init()) {
			return _localProperties.getProperty(property, defaultValue);
		}
		return defaultValue;
	}
}
