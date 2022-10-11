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
	 * http://localhost:8080/firestation?stationNumber=<station_number>
	 * 
	 * @param firestation
	 * @return
	 * @throws AlreadyExistsException
	 */
	@PostMapping("/firestation")
	public ResponseEntity<String> addFireStation(@RequestBody(required = true) FireStation fireStation)
			throws AlreadyExistsException {
		logger.debug("Postmapping - addFireStation");

		return new ResponseEntity<String>(fireStationService.addFireStation(fireStation), HttpStatus.OK);
	}

	@DeleteMapping("/firestation")
	public ResponseEntity<String> deleteFireStation(@RequestBody(required = true) FireStation fireStation)
			throws DataNotFoundException {
		logger.debug("DeleteMapping - deleteFireStation");

		return new ResponseEntity<String>(fireStationService.deleteFireStation(fireStation), HttpStatus.OK);
	}
	
	@PutMapping("/firestation")
	public ResponseEntity<String> updDateFireStation(@RequestBody(required = true) FireStation fireStation)
			throws DataNotFoundException {
		logger.debug("PutMapping - updDateFireStation");

		return new ResponseEntity<String>(fireStationService.updateFireStation(fireStation), HttpStatus.OK);
	}


}
