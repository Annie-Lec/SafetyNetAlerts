package com.safetynet.alerts.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.repository.MedicalRecordsRepository;

@Service
public class MedicalRecordsService {

	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	private static final Logger logger = LogManager.getLogger("MedicalRecordsService");

	/**
	 * 
	 * @param name
	 * @param firstName
	 * @return find a medicalRecord for a given name and firstname
	 * @throws DataNotFoundException
	 */
	public MedicalRecords findMRByNameAndFirstName(String name, String firstName) throws DataNotFoundException {
		MedicalRecords medicalRecords;
		medicalRecords = medicalRecordsRepository.findMRByNameAndFirstName(name, firstName);
		if (medicalRecords != null) {
			return medicalRecords;
		} else {
			logger.error("The person  :{" + name + " - " + firstName + "} not found");
			throw new DataNotFoundException("The person  :{" + name + " - " + firstName + "} not found");
		}
	}

	/**
	 * 
	 * @return the list of all the medical records
	 */
	public List<MedicalRecords> getMedicalRecords() {
		logger.info("In Service, getMedicalRecords : get all the MR");
		return medicalRecordsRepository.getMedicalRecords();
	}

	/**
	 * 
	 * @param medicalRecords
	 * @return a message about the creation of a medical Records
	 * @throws AlreadyExistsException
	 */
	public String addMedicalRecords(MedicalRecords medicalRecords) throws AlreadyExistsException {
		String message;

		if (!(medicalRecords.getFirstName().isEmpty() || medicalRecords.getLastName().isEmpty())) {
			MedicalRecords MRToAdd = medicalRecordsRepository.findMRByNameAndFirstName(medicalRecords.getLastName(),
					medicalRecords.getFirstName());
			if (MRToAdd == null) {
				medicalRecordsRepository.addMedicalRecords(medicalRecords);
				message = "MedicalRecords for : " + medicalRecords.getLastName() + " - " + medicalRecords.getFirstName()
						+ " has been added";
			} else {
				message = "A person already exists with this name and firstName";
				logger.error(message);
				throw new AlreadyExistsException(message);
			}
		} else {
			message = "Name or FirstName don't have to be null";
		}

		return message;
	}
	
	/**
	 * 
	 * @param medicalRecords
	 * @return a message about the delete of a MR
	 * @throws DataNotFoundException
	 */
	public String deleteMedicalRecords(MedicalRecords medicalRecords) throws DataNotFoundException {
		String message;

		if (!(medicalRecords.getFirstName().isEmpty() || medicalRecords.getLastName().isEmpty())) {
			MedicalRecords MRToDelete = medicalRecordsRepository.findMRByNameAndFirstName(medicalRecords.getLastName(),
					medicalRecords.getFirstName());
			if (MRToDelete != null) {
				medicalRecordsRepository.deleteMedicalRecords(medicalRecords);
				message = "MedicalRecords for : " + medicalRecords.getLastName() + " - " + medicalRecords.getFirstName()
						+ " has been deleted";
			} else {
				message = "No MedicalRecords with this name and firstName could'nt be found : unable to delete";
				logger.error(message);
				throw new DataNotFoundException(message);
			}
		} else {
			message = "Name or FirstName don't have to be null";
		}

		return message;
	}
	/**
	 * 
	 * @param medicalRecords
	 * @return a message about a medicalRecords ' update
	 * @throws DataNotFoundException
	 */
	public String updateMedicalRecords(MedicalRecords medicalRecords) throws DataNotFoundException {
		String message;

		if (!(medicalRecords.getFirstName().isEmpty() || medicalRecords.getLastName().isEmpty())) {
			MedicalRecords MRToUpdate = medicalRecordsRepository.findMRByNameAndFirstName(medicalRecords.getLastName(),
					medicalRecords.getFirstName());
			if (MRToUpdate != null) {
				medicalRecordsRepository.updateMedicalRecords(medicalRecords);
				message = "MedicalRecords for : " + medicalRecords.getLastName() + " - " + medicalRecords.getFirstName()
						+ " has been updated";
			} else {
				message = "No MedicalRecords with this name and firstName could'nt be found : unable to update";
				logger.error(message);
				throw new DataNotFoundException(message);
			}
		} else {
			message = "Name or FirstName don't have to be null";
		}

		return message;
	}


}
