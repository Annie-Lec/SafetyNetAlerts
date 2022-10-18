package com.safetynet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");

	@Autowired
	PersonService personService;

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getPersons() {
		logger.debug("Getmapping - getPersons");
		System.out.println("GetMapping PErsons");
		return new ResponseEntity<>(personService.getPersonsService(), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/person
	 * @param person
	 * @return a message about the creation of a person
	 * @throws AlreadyExistsException
	 */
	@PostMapping("/person")
	public ResponseEntity<String> addPerson(@RequestBody(required = true) Person person) throws AlreadyExistsException {
		try {
			String result = personService.addPerson(person);

			logger.debug("Postmapping - addPerson");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
	
	/**
	 * 
	 * @param person
	 * @return message about deleting person
	 * @throws DataNotFoundException
	 */
	@DeleteMapping("/person")
	public ResponseEntity<String> deletePerson(@RequestBody(required = true) Person person) throws DataNotFoundException {
		try {
			String result = personService.deletePerson(person);

			logger.debug("Postmapping - deletePerson");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}
	
	/**
	 * 
	 * @param person
	 * @return mesage about updating person
	 * @throws DataNotFoundException
	 */
	@PutMapping("/person")
	public ResponseEntity<String> upddatePerson(@RequestBody(required = true) Person person) throws DataNotFoundException {
		try {
			String result = personService.updatePerson(person);

			logger.debug("Putmapping - updatePerson");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}



}
