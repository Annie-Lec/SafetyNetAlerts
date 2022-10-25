package com.safetynet.alerts.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.jsoniter.any.Any;
import com.safetynet.alerts.model.MedicalRecords;

/**
 * 
 * class that contains the mapping of medicalRecords data : 
 * JSON to MedicalRecordClass
 * and MedicalRecordsClass to JSON
 * 
 *
 */
@Component
public class MedicalRecordsMapper {

	public static final Logger logger = LogManager.getLogger("MedicalRecordsMapper Class");

	private List<MedicalRecords> medicalRecords = new ArrayList<>();

	public List<MedicalRecords> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public MedicalRecordsMapper() {
	}

	public List<MedicalRecords> mapToMedicalRecordsClass(Any medicalRecordsReader) {
		logger.info("Mapper - mapToMedicalRecordsClass");
		for (Any mr : medicalRecordsReader) {
			//First we map the 2 arrays (medications and allergies) from json to java object
			
			List<String> medicationsObj = new ArrayList<>();
			List<Any> medicationsJson = mr.get("medications").asList();
					
			List<String> allergiesObj = new ArrayList<>();
			List<Any> allergiesJson = mr.get("allergies").asList();
			
			for (Any m : medicationsJson){
				medicationsObj.add(m.toString());
			}
			
			for(Any a : allergiesJson) {
				allergiesObj.add(a.toString());
			}
			
			//Second we can add the 2 arrayList allergie and medication to MedicalRecords
			medicalRecords.add(new MedicalRecords(mr.get("firstName").toString(), mr.get("lastName").toString(),
					mr.get("birthdate").toString(), medicationsObj, allergiesObj));
			
		}
		
		return medicalRecords;
	}
	
	public JSONObject medicalRecordsToJSON(MedicalRecords mr) {
		Map<String, Object> mapMedicalRecords = new HashMap<>();

		mapMedicalRecords.put("firstName", mr.getFirstName());
		mapMedicalRecords.put("lastName", mr.getLastName());
		mapMedicalRecords.put("birthdate", mr.getBirthdate());
		mapMedicalRecords.put("medications", mr.getMedications());
		mapMedicalRecords.put("allergies", mr.getAllergies());
		JSONObject jsonMR = new JSONObject(mapMedicalRecords);
		return jsonMR;

	}

}
