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

import io.swagger.annotations.Api;

@Api("API pour les opérations CRUD sur les MedicalRecords.")
@RestController
public class MedicalRecordsController {

	private static final Logger logger = LogManager.getLogger("MedicalRecordsController");

	@Autowired
	MedicalRecordsService medicalRecordsService;

	private final ResponseEntity<String> responseForBadRequest = ResponseEntity.badRequest()
			.body("Name or FirstName Empty : unable to add/delete/update");

	@GetMapping("/medicalrecords")
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
	 * @throws DataNotFoundException 
	 */
	@PostMapping("/medicalrecord")
	public ResponseEntity<String> addMedicalRecords(@RequestBody(required = true) MedicalRecords medicalRecords)
			throws AlreadyExistsException, DataNotFoundException {
		if (medicalRecords.getFirstName().isEmpty() || medicalRecords.getLastName().isEmpty()) {
			logger.error("Invalid request  HttpStatus : ", HttpStatus.BAD_REQUEST);
			return responseForBadRequest;
		} else {

			try {
				String result = medicalRecordsService.addMedicalRecords(medicalRecords);

				logger.debug("Postmapping - addmedicalRecord");

				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (AlreadyExistsException e) {
				return new ResponseEntity<String>("A person already exists with this name and firstName", HttpStatus.BAD_REQUEST);
			}
		}
	}

	/**
	 * 
	 * @param medicalRecords
	 * @return a message relative to the deletion of a medical record
	 * @throws DataNotFoundException
	 */
	@DeleteMapping("/medicalrecord")
	public ResponseEntity<String> deleteMedicalRecords(@RequestBody(required = true) MedicalRecords medicalRecords)
			throws DataNotFoundException {
		if (medicalRecords.getFirstName().isEmpty() || medicalRecords.getLastName().isEmpty()) {
			logger.error("Invalid request  HttpStatus : ", HttpStatus.BAD_REQUEST);
			return responseForBadRequest;
		} else {

			try {
				String result = medicalRecordsService.deleteMedicalRecords(medicalRecords);

				logger.debug("Postmapping - deletemedicalRecords");

				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (DataNotFoundException e) {
				return new ResponseEntity<String>("No MedicalRecords with this name and firstName could be found : unable to delete", HttpStatus.BAD_REQUEST);

			}
		}
	}

	/**
	 * 
	 * @param medicalRecords
	 * @return a message relative to a medicalRecord 's update
	 * @throws DataNotFoundException
	 */
	@PutMapping("/medicalrecord")
	public ResponseEntity<String> upddateMedicalRecords(@RequestBody(required = true) MedicalRecords medicalRecords)
			throws DataNotFoundException {
		if (medicalRecords.getFirstName().isEmpty() || medicalRecords.getLastName().isEmpty()) {
			logger.error("Invalid request  HttpStatus : ", HttpStatus.BAD_REQUEST);
			return responseForBadRequest;
		} else {

			try {
				String result = medicalRecordsService.updateMedicalRecords(medicalRecords);

				logger.debug("Putmapping - updatemedicalRecords");

				return new ResponseEntity<String>(result, HttpStatus.OK);
			} catch (DataNotFoundException e) {
				return new ResponseEntity<String>("No MedicalRecords with this name and firstName could be found : unable to update", HttpStatus.BAD_REQUEST);
			}
		}
	}
}
