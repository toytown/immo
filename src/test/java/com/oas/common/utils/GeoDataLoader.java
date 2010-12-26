package com.oas.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.oas.test.utils.OASTestCase;

public class GeoDataLoader extends OASTestCase {

	@Autowired
	@Qualifier("jdbcTemplate")
	private SimpleJdbcTemplate jdbcTemplate;
	private static final String GEO_DB_FILE="/home/prasanna/dev/projects/workspace/oas/src/main/config/data/geodb_postal_codes.txt";
	
	@Test
	public void loadGeoPostalCodes() throws IOException {
		
		File file = new File(GEO_DB_FILE);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		while((line = reader.readLine()) != null) {
			Object[] columnsValues = line.split("\\t");
			jdbcTemplate.update("insert into GEODB_POSTAL_CODES (location_id, zip_code, latitude, longitude, city) values (?,?,?,?,?)", columnsValues);
		}
	}
}
