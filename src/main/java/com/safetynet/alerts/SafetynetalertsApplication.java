package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.alerts.service.AlertsService;


@SpringBootApplication
public class SafetynetalertsApplication  {

//	@Autowired
//	IReadDataSource dataSource = new ReadDataSourceFromJson();
//	
//	PersonMapper personMapper = new PersonMapper();
//	@Autowired
//	PersonService personService = new PersonService();
//	@Autowired
//	PersonRepository personRepository = new PersonRepository();
//	@Autowired
//	FireStationService fireStationService = new FireStationService();
	
	@Autowired
	AlertsService alertsService = new AlertsService();

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {

		// System.out.println(personRepository.findPersonsByAddress("951 LoneTree Rd"));
		// System.out.println(personRepository.findAllThePersons());
		//System.out.println(personService.findPersonsByAddress("951 LoneTree Rd"));
//		System.out.println(fireStationService.findAddressByFireStation("3"));
//		System.out.println(alertsService.getSMSforPersonsCoveredByStation("2"));
	}
//}
