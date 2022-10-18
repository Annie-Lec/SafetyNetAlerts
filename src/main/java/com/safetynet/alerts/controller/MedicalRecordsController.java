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
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.service.MedicalRecordsService;

@RestController
public class MedicalRecordsController {

	private static final Logger logger = LogManager.getLogger("MedicalRecordsController");

	@Autowired
	MedicalRecordsService medicalRecordsService;

	@GetMapping("/medicalRecords")
	public ResponseEntity<List<MedicalRecords>> getMedicalRecords() {
		logger.debug("Getmapping - getMedicalRecords");
		System.out.println("GetMapping MedicalRecords");
		return new ResponseEntity<>(medicalRecordsService.getMedicalRecords(), HttpStatus.OK);
	}

	/**
	 * 
	 * @param medicalRecords
	 * @return a message about the creation of a medical records
	 * 
	 * @throws AlreadyExistsException
	 */
	@PostMapping("/medicalRecord")
	public ResponseEntity<String> addMedicalRecords(@RequestBody(required = true) MedicalRecords medicalRecords)
			throws AlreadyExistsException {
		try {
			String result = medicalRecordsService.addMedicalRecords(medicalRecords);

			logger.debug("Postmapping - addmedicalRecord");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 
	 * @param medicalRecords
	 * @return  a message relative to the deletion of a medical record
	 * @throws DataNotFoundException
	 */
	@DeleteMapping("/medicalRecord")
	public ResponseEntity<String> deleteMedicalRecords(@RequestBody(required = true) MedicalRecords medicalRecords)
			throws DataNotFoundException {
		try {
			String result = medicalRecordsService.deleteMedicalRecords(medicalRecords);

			logger.debug("Postmapping - deletemedicalRecords");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * 
	 * @param medicalRecords
	 * @return a message relative to a medicalRecord 's update
	 * @throws DataNotFoundException
	 */
	@PutMapping("/medicalRecord")
	public ResponseEntity<String> upddateMedicalRecords(@RequestBody(required = true) MedicalRecords medicalRecords)
			throws DataNotFoundException {
		try {
			String result = medicalRecordsService.updateMedicalRecords(medicalRecords);

			logger.debug("Putmapping - updatemedicalRecords");

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (DataNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
