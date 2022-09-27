package com.safetynet.alerts.util;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.safetynet.alerts.configuration.CustomProperties;

@Component
public class ReadDataSourceFromJson implements IReadDataSource {

	private static final Logger logger = LogManager.getLogger("ReadDataSourceFromJson");

	@Autowired
	private CustomProperties property;

	Any any;
	
	Any personAny;
	Any fireStationsAny;
	Any medicalRecordsAny;

	public ReadDataSourceFromJson() {
		
	}

	@PostConstruct
	public void readData() {

		try {

			logger.debug("starting loading json file ");
			String fileInString = Files.readString(new File(property.getJsonFilePath()).toPath());
			JsonIterator iter = JsonIterator.parse(fileInString);
			any = iter.readAny();
			
			 personAny = any.get("persons");
			 fireStationsAny = any.get("firestations");
			 medicalRecordsAny = any.get("medicalrecords");

		} catch (IOException e) {
			e.printStackTrace();
		
		}
	}
	
	@Override
	public Object getReadDataPersons() {
		return personAny;
	}
	@Override
	public Object getReadDataFireStations() {
		return fireStationsAny;
	}
	@Override
	public Object getReadDataMedicalRecords() {
		return medicalRecordsAny;
	}

}
