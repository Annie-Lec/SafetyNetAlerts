package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.IReadDataSource;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

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

	@Autowired
	IReadDataSource dataSource = new ReadDataSourceFromJson();
	@Autowired
	PersonMapper personMapper = new PersonMapper();

	public PersonRepository() {
	}

	public PersonRepository(List<Person> persons) {
		this.persons = persons;
	}

	/**
	 * Before running the Person Repository Constructor this method is running to
	 * initiate data source and to extract List of Persons
	 * 
	 */
	@PostConstruct
	public void initPersons() {
		System.out.println("repository : initialize Person with the JSON File");
		setPersons(personMapper.mapToPersonClass((Any) dataSource.getReadDataPersons()));
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
	 * update a person find by name and first name then deleted and added in the
	 * List of Persons
	 * 
	 * @param person
	 */
	public Person updatePerson(Person person)  {
		logger.debug("update a person");
		Person persontoUpdate = findPersonByNameAndFirstName(person.getLastName(), person.getFirstName());
		// l'update se fait par suppression de l'ancienne Personne puis ajout de la
		// nouvelle
		deletePerson(persontoUpdate);
		addPerson(person);
		return person;
	}

	/**
	 * retrieve the whole List of Persons
	 * 
	 */
	public List<Person> findAllThePersons() {
		logger.debug("find a list of persons");
		return persons;
	}

	/**
	 * Retrieve the list of persons at the address
	 * 
	 * @param address
	 */
	public List<Person> findPersonsByAddress(String address)  {
		logger.debug("find a list of persons at an address");
		return persons.stream().filter(p -> p.getAddress().equalsIgnoreCase(address)).collect(Collectors.toList());
	}

	/**
	 * Retrieve the list of persons at living in the city
	 * 
	 * @param address
	 */
	public List<Person> findPersonsByCity(String city)  {
		logger.debug("find a list of persons living in the city ");
		return persons.stream().filter(p -> p.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());
	}

	/**
	 * Retrieve the list of persons with the same last name
	 * 
	 * @param address
	 */
	public List<Person> findPersonsByName(String name)  {
		logger.debug("find a list of persons with the same name : {}", name);
		System.out.println("find a list of persons with the same name");
		return persons.stream().filter(p -> p.getLastName().equalsIgnoreCase(name)).collect(Collectors.toList());
	}

	/**
	 * Retrieve the person with the given last name and the given first name
	 * 
	 * @param name
	 * @param firstName
	 */
	public Person findPersonByNameAndFirstName(String name, String firstName) {
		logger.debug("find THE person with the given first name and Last name: ");
		return persons.stream().filter(p -> p.getLastName().equalsIgnoreCase(name))
				.filter(p -> p.getFirstName().equalsIgnoreCase(firstName)).findFirst().orElse(null);
	}

	
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
