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
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;

@RestController
public class FireStationController {

	private static final Logger logger = LogManager.getLogger("FireStationController");

	@Autowired
	
	FireStationService fireStationService;

	/**
	 * http://localhost:8080/firestation
	 * 
	 * @param none
	 * @return the list of Fire stations
	 * 
	 */
	@GetMapping("/firestations")
	public ResponseEntity<List<FireStation>> getFireStations() {
		logger.debug("Ggetmapping - getFireStations");
		return new ResponseEntity<>(fireStationService.getFireStationsService(), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/firestation
	 * 
	 * @param firestation
	 * @return a message about the creation of a fire station
	 * @throws AlreadyExistsException
	 */
	@PostMapping("/firestation")
	public ResponseEntity<String> addFireStation(@RequestBody(required = true) FireStation fireStation)
			throws AlreadyExistsException {
		try {
			String result = fireStationService.addFireStation(fireStation);

			logger.debug("Postmapping - addFireStation");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * 
	 * @param fireStation
	 * @return a message about the delete done or not
	 * @throws DataNotFoundException
	 */
	@DeleteMapping("/firestation")
	public ResponseEntity<String> deleteFireStation(@RequestBody(required = true) FireStation fireStation)
			throws DataNotFoundException {
		logger.debug("DeleteMapping - deleteFireStation");
		try {
			String result = fireStationService.deleteFireStation(fireStation);

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 
	 * @param fireStation
	 * @return a message relative to the update of a fire station
	 * @throws DataNotFoundException
	 */
	@PutMapping("/firestation")
	public ResponseEntity<String> updDateFireStation(@RequestBody(required = true) FireStation fireStation)
			throws DataNotFoundException {
		logger.debug("PutMapping - updDateFireStation");
		try {
			String result = fireStationService.updateFireStation(fireStation);

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
