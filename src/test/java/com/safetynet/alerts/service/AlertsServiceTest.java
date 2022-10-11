package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

class AlertsServiceTest {
	
	private static List<Person> persons = new ArrayList<>();
	private static List<FireStation> fireStations = new ArrayList<>();
	private static List<MedicalRecords> medicalRecords = new ArrayList<>();
	
	
	@Mock
	private static PersonService personService;
	@Mock
	private static FireStationService fireStationService;
	@Mock
	private static MedicalRecordsService medicalRecordService;
	@InjectMocks
	private static AlertsService alertService;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		persons.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		persons.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));
		persons.add(new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3"));
		persons.add(new Person("firstName4", "lastName4", "address4", "city2", "5678", "0173456486", "email4"));
		persons.add(new Person("firstName5", "lastName5", "address3", "city2", "5678", "0127446322", "email5"));
	
		fireStations.add(new FireStation("address1", 1));
		fireStations.add(new FireStation("address2", 1));
		fireStations.add(new FireStation("address3", 2));
		fireStations.add(new FireStation("address4", 1));
		fireStations.add(new FireStation("address5", 2));
		
		medicalRecords.add(new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		medicalRecords.add(new MedicalRecords("firstName2", "lastName1", "25/12/2005", List.of("pivalone 60"), List.of("rhume Des Foins", "acariens")));
		medicalRecords.add(new MedicalRecords("firstName3", "lastName3", "25/12/1965", List.of(), List.of()));
		medicalRecords.add(new MedicalRecords("firstName4", "lastName4", "25/12/1932", List.of("doliprane 1000mg" , "cardio 200"), List.of("iode")));
		medicalRecords.add(new MedicalRecords("firstName5", "lastName5", "25/12/1948", List.of("doliprane 1000mg" , "tramadol 100"), List.of()));

	}

	@Test
	void testGetPhoneforPersonsCoveredByStation() {
		fail("Not yet implemented");
	}

}
