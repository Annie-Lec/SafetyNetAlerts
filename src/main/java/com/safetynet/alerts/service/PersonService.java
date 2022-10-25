package com.safetynet.alerts.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.WriteDataInJson;

@Service
public class PersonService {

	private static final Logger logger = LogManager.getLogger("PersonService");

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	WriteDataInJson writeData;

	/**
	 * find a list of Person by the address
	 * 
	 * @param address
	 * @return a list of Person
	 */
	public List<Person> findPersonsByAddress(String address) throws DataNotFoundException {
		List<Person> persons = null;

		persons = personRepository.findPersonsByAddress(address);

		if (persons != null) {
			return persons;
		} else {
			logger.error("The address  :{" + address + "} not found, couldn't retrieve persons");
			throw new DataNotFoundException("The address  :{" + address + "} not found, couldn't retrieve persons");
		}

	}

	/**
	 * find a list of Person with the same Last Name
	 * 
	 * @param lastName
	 * @return a list of Person
	 */
	public List<Person> findPersonsByLastName(String lastName) throws DataNotFoundException {

		List<Person> persons = null;

		persons = personRepository.findPersonsByName(lastName);
		if (persons != null) {
			return persons;
		} else {
			logger.error("The name  :{" + lastName + "} not found, couldn't retrieve persons");
			throw new DataNotFoundException("The name  :{" + lastName + "} not found, couldn't retrieve persons");
		}
	}

	/**
	 * find a Person with his last name and his first name
	 * 
	 * @param lastName
	 * @param firstName
	 * @return a Person
	 */
	public Person findPersonByLastNameAndFirstName(String lastName, String firstName) throws DataNotFoundException {

		Person person = null;

		person = personRepository.findPersonByNameAndFirstName(lastName, firstName);

		if (person != null) {
			return person;
		} else {
			logger.error("The person  :{" + lastName + " - " + firstName + "} not found");
			throw new DataNotFoundException("The person  :{" + lastName + " - " + firstName + "} not found");
		}

	}

	/**
	 * retrieve all the Person of a city
	 * 
	 * @param city
	 * @return a list of Person
	 * @throws DataNotFoundException 
	 */
	public List<Person> findPersonsByCity(String city) throws DataNotFoundException {

		List<Person> persons = null;
		persons = personRepository.findPersonsByCity(city);
		if (persons != null) {
		return persons;
		} else {
			logger.error("The city  :{" + city + "} not found, couldn't retrieve persons");
			throw new DataNotFoundException("The city  :{" + city + "} not found, couldn't retrieve persons");
		}
		
	}

	/// CRUD Methods

	/**
	 * READ the List Of Persons
	 */
	public List<Person> getPersonsService() {
		logger.debug("get all the firestation ");
		return personRepository.findAllThePersons();
	}

	/**
	 * 
	 * @param person
	 * @return a message about the creation of a person
	 * @throws AlreadyExistsException
	 * @throws DataNotFoundException 
	 */
	public String addPerson(Person person) throws AlreadyExistsException, DataNotFoundException {
		String message;
		if (!(person.getFirstName().isEmpty() || person.getLastName().isEmpty())) {
			Person personToAdd = personRepository.findPersonByNameAndFirstName(person.getLastName(),
					person.getFirstName());
			if (personToAdd == null) {
				personRepository.addPerson(person);
				writeData.writeInFile();
				message = "Person : " + person.getLastName() + " - " + person.getFirstName() + " has been added";
			} else {
				message = "A person already exists with this name and firstName";
				logger.error(message);
				throw new AlreadyExistsException(message);
			}
		} else {
			message = "Name or FirstName Empty : unable to add/delete/update";
			logger.error(message);
			throw new DataNotFoundException(message);
		}
		return message;
	}

	/**
	 * 
	 * @param person
	 * @return a message about the deleting of a person
	 * @throws DataNotFoundException
	 */
	public String deletePerson(Person person) throws DataNotFoundException {
		String message;
		if (!(person.getFirstName().isEmpty() || person.getLastName().isEmpty())) {
			Person personToDelete = personRepository.findPersonByNameAndFirstName(person.getLastName(),
					person.getFirstName());
			if (personToDelete != null) {
				personRepository.deletePerson(person);
				writeData.writeInFile();
				message = "Person : " + person.getLastName() + " - " + person.getFirstName() + " has been deleted";
			} else {
				message = "No person with this name and firstName could be found : unable to delete";
				logger.error(message);
				throw new DataNotFoundException(message);
			}
		} else {
			message = "Name or FirstName Empty : unable to add/delete/update";
			logger.error(message);
			throw new DataNotFoundException(message);
		}
		return message;
	}
	
	/**
	 * 
	 * @param person
	 * @return a message relative to a person's update
	 * @throws DataNotFoundException
	 */
	public String updatePerson(Person person) throws DataNotFoundException {
		String message;
		if (!(person.getFirstName().isEmpty() || person.getLastName().isEmpty())) {
			Person personToUpdate = personRepository.findPersonByNameAndFirstName(person.getLastName(),
					person.getFirstName());
			if (personToUpdate != null) {
				personRepository.updatePerson(person);		
				writeData.writeInFile();
				message = "Person : " + person.getLastName() + " - " + person.getFirstName() + " has been updated";
			} else {
				message = "No person with this name and firstName could be found : unable to update";
				logger.error(message);
				throw new DataNotFoundException(message);
			}
		} else {
			message = "Name or FirstName Empty : unable to add/delete/update";
			logger.error(message);
			throw new DataNotFoundException(message);
		}
		return message;
	}
	
	

}
