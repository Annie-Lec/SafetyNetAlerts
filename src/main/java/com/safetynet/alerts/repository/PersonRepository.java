package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

/**
 * 
 * class that contains the management of person's data : CRUD and more like find
 * by city for example
 *
 */
@Repository
public class PersonRepository {
	private static final Logger logger = LogManager.getLogger("PersonRepository");

	private List<Person> persons = new ArrayList<>();

	public PersonRepository() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param persons
	 */
	public PersonRepository(List<Person> persons) {
		this.persons = persons;
	}

	/**
	 * Save a person in the list of persons
	 * 
	 * @param person
	 */
	public void addPerson(Person person) {
		logger.info("saving a person in the data listof persons", person);
		this.persons.add(person);
	}

	/**
	 * delete a person from the list of persons
	 * 
	 * @param person
	 */
	public void deletePerson(Person person) {
		logger.debug("deleting person {} from the list of persons ", person);
		this.persons.remove(person);
	}

	/**
	 * search a person by last name and first name
	 * 
	 * @param firstName
	 * @param lastName
	 * @return a person
	 */
	public Person getByName(String firstName, String lastName) {
		logger.debug("searching the person {} {} inthe list of persons", firstName, lastName);
		return persons.stream().filter(person -> person.getLastName().equalsIgnoreCase(lastName))
				.filter(person -> person.getFirstName().equalsIgnoreCase(firstName)).findFirst().orElse(null);

	}

}
