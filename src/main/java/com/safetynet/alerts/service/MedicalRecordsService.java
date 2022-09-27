package com.safetynet.alerts.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.repository.MedicalRecordsRepository;

@Service
public class MedicalRecordsService {

	@Autowired
	MedicalRecordsRepository medicalRecordsRepository;

	public MedicalRecords findMRByNameAndFirstName(String name, String firstName) throws IOException {
		MedicalRecords medicalRecords;
		medicalRecords = medicalRecordsRepository.findMRByNameAndFirstName(name, firstName);
		return medicalRecords;
	}

}
