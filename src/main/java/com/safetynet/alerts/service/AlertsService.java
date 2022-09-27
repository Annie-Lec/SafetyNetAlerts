package com.safetynet.alerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.AgeCalculator;

@Service
public class AlertsService {

	private static final Logger logger = LogManager.getLogger("AlertsService");

	@Autowired
	PersonService personService;
	@Autowired
	FireStationService fireStationService;
	@Autowired
	MedicalRecordsService medicalRecordsService;

	private JSONObject bodyJSON = new JSONObject();

	/**
	 * Define the list of phone numbers to send emergency SMS - used by URL3
	 * 
	 * Endpoint from phoneAlert?firestation=<firestation_number>
	 * 
	 * @param numberFireStation
	 * @return the JSONlist of phone number to allow emergency services to send SMS
	 */
	public JSONObject getPhoneforPersonsCoveredByStation(int numberFireStation) {
		logger.info("create List of phone number for URL3 - getSMSforPersonsCoveredByStation() ");
		List<Person> personsAtAnAddress = new ArrayList<>();
		List<Person> personsAtSeveralAddresses = new ArrayList<>();
		HashMap<String, Object> bodyHashMap = new HashMap<String, Object>();

		List<String> addresses;
		try {
			addresses = fireStationService.findAddressByFireStation(numberFireStation);

			for (String address : addresses) {
				personsAtAnAddress = personService.findPersonsByAddress(address);

				for (Person p : personsAtAnAddress) {
					personsAtSeveralAddresses.add(p);
				}
			}

			bodyHashMap.put("phone",
					personsAtSeveralAddresses.stream().map(Person::getPhone).distinct().collect(Collectors.toList()));
			bodyJSON = new JSONObject(bodyHashMap);
			return bodyJSON;
		} catch (IOException e) {
			logger.error("problem with firestation number");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Define the list of personnes couvertes par la caserne de pompiers
	 * correspondante
	 * 
	 * Endpoint from firestation?stationNumber=<station_number>
	 * 
	 * @param numberFireStation
	 * @return the JSONlist of person covered by a station number
	 */

	public JSONObject getListOfPersonsCoveredByStation(int numberFireStation) {
		logger.info("create List of phone number for URL3 - getSMSforPersonsCoveredByStation() ");
		List<Person> personsAtAnAddress = new ArrayList<>();
		List<String> personsCovered = new ArrayList<>();
		HashMap<String, Object> bodyHashMap = new HashMap<String, Object>();
		int nbAdults = 0;
		int nbChildren = 0;

		List<String> addresses;
		try {
			addresses = fireStationService.findAddressByFireStation(numberFireStation);

			for (String address : addresses) {
				personsAtAnAddress = personService.findPersonsByAddress(address);

				for (Person p : personsAtAnAddress) {
					personsCovered.add("firstName : " + p.getFirstName() + ", " + "lastName : " + p.getLastName() + ", "
							+ "address : " + p.getAddress() + ", " + "phone : " + p.getPhone());
					bodyHashMap.put("personscoveredbystation", personsCovered);
					MedicalRecords medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(),
							p.getFirstName());
					if (AgeCalculator.calculate(medicalRecords.getBirthdate()) > 18) {
						nbAdults++;
					} else {
						nbChildren++;
					}
				}
			}
			bodyHashMap.put("nbAdults", nbAdults);
			bodyHashMap.put("nbChildren", nbChildren);

			bodyJSON = new JSONObject(bodyHashMap);

			return bodyJSON;
		} catch (IOException e) {
			logger.error("problem with firestation number");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Define the list of children at an address
	 * 
	 * Endpoint from childAlert?adsress=<address>
	 * 
	 * @param address
	 * @return the JSONlist of person covered by a station number
	 */

	public JSONObject getListOfChildrenAtAnAdress(String address) {
		List<Person> personsAtAnAddress = new ArrayList<>();
		HashMap<String, Object> bodyHashMap = new HashMap<String, Object>();
		List<String> childrenCovered = new ArrayList<>();
		List<String> adults = new ArrayList<>();
		int age;
		int childAtTheAddress = 0;

		personsAtAnAddress = personService.findPersonsByAddress(address);

		for (Person p : personsAtAnAddress) {
			MedicalRecords medicalRecords;
			try {
				medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(), p.getFirstName());

				age = AgeCalculator.calculate(medicalRecords.getBirthdate());
				if (age <= 18) {
					childrenCovered.add("firstName : " + p.getFirstName() + ", " + "lastName : " + p.getLastName()
							+ " - " + "age : " + age);
					childAtTheAddress++;
				} else {
					adults.add("firstName : " + p.getFirstName() + ", " + "lastName : " + p.getLastName());
				}

				if (childAtTheAddress > 0) {
					bodyHashMap.put("childrenAtTheAddress", childrenCovered);
					bodyHashMap.put("otherInhabitants", adults);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		bodyJSON = new JSONObject(bodyHashMap);

		return bodyJSON;

	}
}
