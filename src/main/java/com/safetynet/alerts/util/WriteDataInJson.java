package com.safetynet.alerts.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.mapper.MedicalRecordsMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordsRepository;
import com.safetynet.alerts.repository.PersonRepository;

@Component
public class WriteDataInJson {

	 private static final Logger logger = LogManager.getLogger("WriteDataInJson");

	List<Person> persons = new ArrayList<Person>();
	List<FireStation> fireStations;
	List<MedicalRecords> medicalRecords;

	PersonRepository personRepository;
	FireStationRepository fireStationRepository;
	MedicalRecordsRepository medicalRecordsRepository;
	
	
	@Autowired
	PersonMapper personMapper ;
	@Autowired
	FireStationMapper fireStationMapper;
	@Autowired
	MedicalRecordsMapper medicalRecordsMapper;

	/**
	 * @param personRepository
	 * @param fireStationRepository
	 * @param medicalRecordRepository
	 */
	@Autowired
	public WriteDataInJson(PersonRepository personRepository, FireStationRepository fireStationRepository,
			MedicalRecordsRepository medicalRecordsRepository) {
		this.personRepository = personRepository;
		this.fireStationRepository = fireStationRepository;
		this.medicalRecordsRepository = medicalRecordsRepository;
		persons = personRepository.findAllThePersons();
		fireStations = fireStationRepository.getFireStations();
		medicalRecords = medicalRecordsRepository.getMedicalRecords();

	}

	public WriteDataInJson() {
		persons = new ArrayList<Person>();
		fireStations = new ArrayList<FireStation>();
		medicalRecords = new ArrayList<MedicalRecords>();
	}

	public void writeInFile() {
		
		logger.debug("WriteDataInJson() - writeInFile");

		JSONArray jArrayPerson = new JSONArray();
		JSONArray jArrayFireStation = new JSONArray();
		JSONArray jArrayMedicalRecords = new JSONArray();

		for (int i = 0; i < persons.size(); i++) {

			jArrayPerson.add(personMapper.personToJSON(persons.get(i)));
			
		}

		for (int i = 0; i < fireStations.size(); i++) {

			jArrayFireStation.add(fireStationMapper.fireStationToJSON(fireStations.get(i)));
		}

		for (int i = 0; i < medicalRecords.size(); i++) {

			jArrayMedicalRecords.add(medicalRecordsMapper.medicalRecordsToJSON(medicalRecords.get(i)));
		}

		Map<String, Object> map = new HashMap<>();
		
		map.put("persons", jArrayPerson);
		map.put("firestations", jArrayFireStation);
		map.put("medicalrecords", jArrayMedicalRecords);

		JSONObject json = new JSONObject(map);
		System.out.println( json);

		try {

			Writer file = new FileWriter("src/main/resources/static/data.json");

			json.writeJSONString(file);
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	

}
