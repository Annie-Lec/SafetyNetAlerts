package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	/**
	 * find a list of Person by the address
	 * @param address
	 * @return a list of Person
	 */
	public List<Person> findPersonsByAddress(String address) {
		List<Person> persons = null;

		persons = personRepository.findPersonsByAddress(address);

		return persons;
	}

	/**
	 * find a list of Person with the same Last Name
	 * @param lastName
	 * @return a list of Person
	 */
	public List<Person> findPersonsByLastName(String lastName) {

		List<Person> persons = null;

		persons = personRepository.findPersonsByName(lastName);

		return persons;
	}

	/**
	 * find a Person with his last name and his first name
	 * @param lastName
	 * @param firstName
	 * @return a Person
	 */
	public Person findPersonByLastNameAndFirstName(String lastName, String firstName) {

		Person person = null;

		person = personRepository.findPersonByNameAndFirstName(firstName, lastName);

		return person;
	}

	/**
	 * retrieve all the Person of a city
	 * @param city
	 * @return a list of Person
	 */
	public List<Person> findPersonsByCity(String city) {

		List<Person> persons = null;
		persons = personRepository.findPersonsByCity(city);
		return persons;
	}

}
