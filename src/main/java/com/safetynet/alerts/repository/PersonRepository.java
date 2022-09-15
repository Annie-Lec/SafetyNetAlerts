package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

/**
 * 
 * class that contains the management of person's data : CRUD and more like find by city for example
 *
 */
@Repository
public class PersonRepository {
private static final Logger logger = LogManager.getLogger("PersonRepository");
	
	

	public PersonRepository() {
		// TODO Auto-generated constructor stub
	}
	
	private List<Person> persons = new ArrayList<>();
	
	public void addPerson(Person person) {
		logger.debug("saving a person in data ", person);
		this.persons.add(person);
	}

}
