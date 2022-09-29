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

import com.safetynet.alerts.dto.InhabitantsCoveredDTO;
import com.safetynet.alerts.dto.ListOfChildAlertDTO;
import com.safetynet.alerts.dto.NameFirstnameAndAgeDTO;
import com.safetynet.alerts.dto.PersonsCoveredDTO;
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
	 * @return the List<String> of phone number to allow emergency services to send
	 *         SMS
	 */
	public List<String> getPhoneforPersonsCoveredByStation(int numberFireStation) {
		logger.info("create List of phone number for URL3 - getSMSforPersonsCoveredByStation() ");
		List<Person> personsAtAnAddress = new ArrayList<>();

		List<String> phone = new ArrayList<>();

		List<String> addresses;
		try {
			addresses = fireStationService.findAddressByFireStation(numberFireStation);

			for (String address : addresses) {
				personsAtAnAddress.addAll(personService.findPersonsByAddress(address));

			}

			phone = personsAtAnAddress.stream().map(Person::getPhone).distinct().collect(Collectors.toList());

			return phone;
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
	 * @return the public InhabitantsCoveredDTO list of person covered by a station
	 *         number
	 */

	public InhabitantsCoveredDTO getListOfPersonsCoveredByStation(int numberFireStation) {
		logger.info("create List of people  for URL1 - getListOfPersonsCoveredByStation() ");
		List<PersonsCoveredDTO> personsCoveredDTO = new ArrayList<>();
		List<Person> personsAtAnAddress = new ArrayList<>();

	
		int nbAdults = 0;
		int nbChildren = 0;
		List<String> addresses;

		try {
			addresses = fireStationService.findAddressByFireStation(numberFireStation);

			for (String address : addresses) {
				personsAtAnAddress = personService.findPersonsByAddress(address);

				for (Person p : personsAtAnAddress) {
					personsCoveredDTO.add(
							new PersonsCoveredDTO(p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone()));
					MedicalRecords medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(),
							p.getFirstName());
					if (AgeCalculator.calculate(medicalRecords.getBirthdate()) > 18) {
						nbAdults++;
					} else {
						nbChildren++;
					}
				}
			}

			return new InhabitantsCoveredDTO(personsCoveredDTO, nbAdults, nbChildren);
		} catch (IOException e) {
			logger.error("problem with firestation number");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Define the list of children at an address
	 * 
	 * Endpoint from childAlert?address=<address>
	 * 
	 * @param address
	 * @return the JSONlist of person covered by a station number
	 */

	public ListOfChildAlertDTO getListOfChildrenAtAnAdress(String address) {
		List<Person> personsAtAnAddress = new ArrayList<>();
		List<NameFirstnameAndAgeDTO> childrenCovered = new ArrayList<>();
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
					childrenCovered.add(new NameFirstnameAndAgeDTO(p.getLastName(), p.getFirstName(), age));
					childAtTheAddress++;
				} else {
					adults.add(p.getLastName() + " " + p.getFirstName());
				}

				
			} catch (IOException e) {
				logger.error("problem with the address");
				System.out.println("pb address");
				e.printStackTrace();
				return null;
			}
		}
		
		if (childAtTheAddress > 0) {
			return new ListOfChildAlertDTO(childrenCovered, adults);
		} else {
			return null;
		}

	}
}
