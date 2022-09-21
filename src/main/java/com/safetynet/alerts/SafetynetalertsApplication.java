package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jsoniter.any.Any;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.IReadDataSource;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootApplication
public class SafetynetalertsApplication implements CommandLineRunner {

//	@Autowired
//	IReadDataSource dataSource = new ReadDataSourceFromJson();
//	
//	PersonMapper personMapper = new PersonMapper();
	@Autowired
	PersonService personService = new PersonService();
//	@Autowired
//	PersonRepository personRepository = new PersonRepository();

	

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//System.out.println(personRepository.findPersonsByAddress("951 LoneTree Rd"));
		//System.out.println(personRepository.findAllThePersons());
	personService.findPersonsByAddress("951 LoneTree Rd");
	}
}
