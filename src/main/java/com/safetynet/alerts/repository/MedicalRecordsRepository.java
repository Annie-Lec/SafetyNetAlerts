package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.alerts.mapper.MedicalRecordsMapper;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.util.IReadDataSource;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

@Repository
public class MedicalRecordsRepository {

	private static final Logger logger = LogManager.getLogger("MedicalRecords Repository");

	private List<MedicalRecords> medicalRecords = new ArrayList<>();

	@Autowired
	MedicalRecordsMapper medicalRecordsMapper;
	@Autowired
	IReadDataSource dataSource = new ReadDataSourceFromJson();

	@PostConstruct
	public void initMedicalRecords() {
		logger.info("repository - initMedicalRecords()");
		setMedicalRecords(medicalRecordsMapper.mapToMedicalRecordsClass((Any) dataSource.getReadDataMedicalRecords()));
	}

	public List<MedicalRecords> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public MedicalRecordsRepository() {	}

	public MedicalRecordsRepository(List<MedicalRecords> medicalRecords) {
		super();
		this.medicalRecords = medicalRecords;
	}
	
	/**
	 * Save a medical Record in the list of medicalRecords
	 * @param medicalRecord
	 */
	public void addMedicalRecords(MedicalRecords medicalRecord) {
		logger.info("save a medicalRecord in the data listof medicalRecord");
		this.medicalRecords.add(medicalRecord);
		
	}
	
	/**
	 * delete a medical Record in the list of medicalRecords
	 * @param medicalRecord
	 */
	public void deleteMedicalRecords(MedicalRecords medicalRecord) {
		logger.info("delete a medicalRecord in the data listof medicalRecord");
		this.medicalRecords.remove(medicalRecord);
		
	}
	
	/**
	 * Retrieve the medical records for the person with the given last name and the given first name
	 * 
	 * @param name
	 * @param firstName
	 */
	public MedicalRecords findMRByNameAndFirstName(String name, String firstName)  {
		logger.debug("find THE Medical Records with the given first name and Last name: ");
		return medicalRecords.stream().filter(p -> p.getLastName().equalsIgnoreCase(name))
				.filter(p -> p.getFirstName().equalsIgnoreCase(firstName)).findFirst().orElse(null);
	}
	
	
	/**
	 * update a medical records by deleting the old record and adding the updated one
	 * @param mr a medicla records
	 * @return
	 */
	public MedicalRecords updateMedicalRecords(MedicalRecords mr) {
		logger.debug("update a Medical Record");
		MedicalRecords mrToUpdate = findMRByNameAndFirstName(mr.getLastName(), mr.getFirstName());
		deleteMedicalRecords(mrToUpdate);
		addMedicalRecords(mr);
		return mr;
	}
	/**
	 * Retrieve the medical records for all the people with the same family name 
	 * 
	 * @param name
	 * 
	 */
//	public List<MedicalRecords> findMRByFamilyName(String name) {
//		logger.debug("find THE Medical Records for all the people with the same family name: ");
//		return medicalRecords.stream().filter(p -> p.getLastName().equalsIgnoreCase(name))
//				.collect(Collectors.toList());
//	}



}
