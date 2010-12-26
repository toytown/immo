package com.oas.common.utils;

import java.util.Properties;

public class OASAplicationConfiguration {

	private Properties properties;
	private static final String IMAGE_STORE_LOCATION ="imagestore.location";
		
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getOASProperty(String key) {
		return (String) properties.get(key);
	}
	
	public String getImageStore() {
		return getOASProperty(OASAplicationConfiguration.IMAGE_STORE_LOCATION);
	}

}
