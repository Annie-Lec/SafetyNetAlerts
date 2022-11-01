package com.safetynet.alerts.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

import io.swagger.v3.oas.annotations.tags.Tag;


//@Api("API pour les opérations CRUD sur les Persons.")
@RestController
@Tag(name = "API for Person", description = "API pour les opérations CRUD sur les Persons")
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");

	@Autowired
	PersonService personService;
	
	
	private final ResponseEntity<String> responseForBadRequest = ResponseEntity.badRequest()
			.body("Name or FirstName Empty : unable to add/delete/update");

//	 @ApiOperation(value = "Récupère toute la liste des personnes")
	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getPersons() {
		logger.debug("Getmapping - getPersons");
		
		return new ResponseEntity<>(personService.getPersonsService(), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/person
	 * 
	 * @param person
	 * @return a message about the creation of a person
	 * @throws AlreadyExistsException
	 * @throws DataNotFoundException
	 */
	// @ApiOperation(value = "Crée une personne")
	@PostMapping("/person")
	public ResponseEntity<String> addPerson(@RequestBody(required = true) Person person)
	//public ResponseEntity<String> addPerson(@PathVariable Person person)
			throws AlreadyExistsException, DataNotFoundException {
		logger.debug("begin PersonController.addPerson");

		if (person.getFirstName().isEmpty() || person.getLastName().isEmpty()) {
			logger.error("Invalid request  HttpStatus : ", HttpStatus.BAD_REQUEST);
			return responseForBadRequest;
		} else {
			try {
				String result = personService.addPerson(person);

				logger.debug("Postmapping - addPerson");
				
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (AlreadyExistsException e) {
				return new ResponseEntity<String>("A person already exists with this name and firstName", HttpStatus.BAD_REQUEST);
			}
		}
	}

	/**
	 * 
	 * @param person
	 * @return message about deleting person
	 * @throws DataNotFoundException
	 */
	@DeleteMapping("/person")
	public ResponseEntity<String> deletePerson(@RequestBody(required = true) Person person)
			throws DataNotFoundException {
		logger.debug("begin PersonController.deletePerson");

		if (person.getFirstName().isEmpty() || person.getLastName().isEmpty()) {
			logger.error("Invalid request  HttpStatus : ", HttpStatus.BAD_REQUEST);
			return responseForBadRequest;
		} else {

			try {
				String result = personService.deletePerson(person);

				logger.debug("Postmapping - deletePerson");

				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (DataNotFoundException e) {
				return new ResponseEntity<String>("No person with this name and firstName could be found : unable to delete", HttpStatus.BAD_REQUEST);

			}
		}
	}

	/**
	 * 
	 * @param person
	 * @return mesage about updating person
	 * @throws DataNotFoundException
	 */
	@PutMapping("/person")
	public ResponseEntity<String> upddatePerson(@RequestBody(required = true) Person person)
			throws DataNotFoundException {
		logger.debug("begin PersonController.upddatePerson");

		if (person.getFirstName().isEmpty() || person.getLastName().isEmpty()) {
			logger.error("Invalid request  HttpStatus : ", HttpStatus.BAD_REQUEST);
			return responseForBadRequest;
		} else {

			try {
				String result = personService.updatePerson(person);
				logger.debug("Putmapping - updatePerson");
				return new ResponseEntity<String>(result, HttpStatus.OK);
			
			} catch (DataNotFoundException e) {
				return new ResponseEntity<String>("No person with this name and firstName could be found : unable to update", HttpStatus.BAD_REQUEST);
			}
		}
	}

}
