package com.safetynet.alerts.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	public List<Person> findPersonsByAddress(String address) {
		List<Person> persons = null;
		try {
			persons = personRepository.findPersonsByAddress(address);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return persons;
	}

	public List<Person> findPersonsByLastName(String lastName) {

		List<Person> persons = null;
		try {
			persons = personRepository.findPersonsByName(lastName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return persons;
	}
	
	public Person findPersonByLastNameAndFirstName(String lastName, String firstName) {

		Person person = null;
		try {
			person = personRepository.findPersonByNameAndFirstName(firstName, lastName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	public List<Person> findPersonsByCity(String city) {

		List<Person> persons = null;
		try {
			persons = personRepository.findPersonsByCity(city);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return persons;
	}

}
