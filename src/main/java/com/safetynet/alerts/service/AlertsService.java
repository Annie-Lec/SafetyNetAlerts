package com.safetynet.alerts.service;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.InfoAddressDTO;
import com.safetynet.alerts.dto.InfoForFirstAndLastNameDTO;
import com.safetynet.alerts.dto.InfoStationDTO;
import com.safetynet.alerts.dto.InhabitantsAtAnaddressDTO;
import com.safetynet.alerts.dto.InhabitantsCoveredDTO;
import com.safetynet.alerts.dto.ListOfChildAlertDTO;
import com.safetynet.alerts.dto.NameAndFirstNameDTO;
import com.safetynet.alerts.dto.NameFirstnameAndAgeDTO;
import com.safetynet.alerts.dto.PersonsCoveredDTO;
import com.safetynet.alerts.exceptions.DataNotFoundException;
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

	/**
	 * Define the list of phone numbers to send emergency SMS - used by URL3
	 * 
	 * Endpoint from phoneAlert?firestation=<firestation_number>
	 * 
	 * @param numberFireStation
	 * @return the List<String> of phone number to allow emergency services to send
	 *         SMS
	 * @throws DataNotFoundException
	 */
	public List<String> getPhoneforPersonsCoveredByStation(int numberFireStation) throws DataNotFoundException {
		logger.info("create List of phone number for URL3 - getSMSforPersonsCoveredByStation() ");

		List<Person> personsAtAnAddress = new ArrayList<>();
		List<String> phone = new ArrayList<>();
		List<String> addresses;

		addresses = fireStationService.findAddressByFireStation(numberFireStation);

		if (!(addresses == null)) {
			for (String address : addresses) {
				personsAtAnAddress.addAll(personService.findPersonsByAddress(address));
			}

			phone = personsAtAnAddress.stream().map(Person::getPhone).distinct().collect(Collectors.toList());
		}
		return phone;

	}

	/**
	 * This url returns a list of people covered by the corresponding
	 * "station_number" fire station. So, if station number = 1, it must return the
	 * inhabitants covered by station number 1.
	 * 
	 * The list includes the following specific information: first name, last name,
	 * address, telephone number. Furthermore, it provides a count of the number of
	 * adults and the number of children (any individual aged 18 or less) in the
	 * area.
	 * 
	 * Endpoint from firestation?stationNumber=<station_number>
	 * 
	 * @param numberFireStation
	 * @return the list of person covered by a station number
	 * @throws DataNotFoundException
	 */

	public InhabitantsCoveredDTO getListOfPersonsCoveredByStation(int numberFireStation) throws DataNotFoundException {
		logger.info("create List of people  for URL1 - getListOfPersonsCoveredByStation() ");

		List<PersonsCoveredDTO> personsCovered = new ArrayList<>();
		List<Person> personsAtAnAddress = new ArrayList<>();

		int nbAdults = 0;
		int nbChildren = 0;
		List<String> addresses;

		addresses = fireStationService.findAddressByFireStation(numberFireStation);
		if (!(addresses == null)) {

			for (String address : addresses) {
				personsAtAnAddress = personService.findPersonsByAddress(address);

				for (Person p : personsAtAnAddress) {
					personsCovered.add(
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
		}
		return new InhabitantsCoveredDTO(personsCovered, nbAdults, nbChildren);

	}

	/**
	 * Define the list of children at an address. The list includes each child's
	 * first and last name, age, and a list of other household members. If there are
	 * no children, this url may return an empty string.
	 * 
	 * Endpoint from childAlert?address=<address>
	 * 
	 * @param address
	 * @return the List of children / others at an address
	 */

	public ListOfChildAlertDTO getListOfChildrenAtAnAdress(String address) {
		logger.info("create List of info by children at an address : getListOfChildrenAtAnAdress ");

		List<Person> personsAtAnAddress = new ArrayList<>();
		List<NameFirstnameAndAgeDTO> childrenCovered = new ArrayList<>();
		List<NameAndFirstNameDTO> adults = new ArrayList<>();
		int age;
		int childAtTheAddress = 0;

		ListOfChildAlertDTO listChildAlert = new ListOfChildAlertDTO();

		try {
			// We have to manage exception : data (address ) not found
			personsAtAnAddress = personService.findPersonsByAddress(address);

			for (Person p : personsAtAnAddress) {
				MedicalRecords medicalRecords;

				medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(), p.getFirstName());

				age = AgeCalculator.calculate(medicalRecords.getBirthdate());
				if (age <= 18) {

					childrenCovered.add(new NameFirstnameAndAgeDTO(p.getFirstName(), p.getLastName(), age));
					childAtTheAddress++;
				} else {
					adults.add(new NameAndFirstNameDTO(p.getLastName(), p.getFirstName()));
				}
			}
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}

		if (childAtTheAddress > 0) {
			listChildAlert = new ListOfChildAlertDTO(childrenCovered, adults);
		}

		return listChildAlert;
	}

	/**
	 * This url returns the list of inhabitants living at the given address and the
	 * number of the fire station serving it. The list should include each person's
	 * name, phone number, age, and medical history (medications, dosages, and
	 * allergies).
	 * 
	 * http://localhost:8080/fire?address=<address> *
	 * 
	 * @param address
	 * @return the list of inhabitants at an address
	 * @throws DataNotFoundException
	 */
	public InfoAddressDTO getListOfInhabitantsAtAnAddress(String address) {
		logger.info("create List of info by inhabitants at an address : getListOfInhabitantsAtAnAddress ");

		List<Person> personsAtAnAddress = new ArrayList<>();
		List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO = new ArrayList<>();
		InfoAddressDTO listOfInhabitants = new InfoAddressDTO();
		int fireStation;
		int age;

		try {
			fireStation = fireStationService.findTheNumberOfFirestationByAddress(address);
			personsAtAnAddress = personService.findPersonsByAddress(address);

			for (Person p : personsAtAnAddress) {
				MedicalRecords medicalRecords;
				medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(), p.getFirstName());
				age = AgeCalculator.calculate(medicalRecords.getBirthdate());

				inhabitantsAtAnaddressDTO.add(new InhabitantsAtAnaddressDTO(p.getLastName(), p.getFirstName(),
						p.getPhone(), age, medicalRecords.getMedications(), medicalRecords.getAllergies()));

			}

//			listOfInhabitants.add(new InfoAddressDTO(inhabitantsAtAnaddressDTO, "address : " + address,
//					"fireStation : " + fireStation));
			listOfInhabitants = new InfoAddressDTO(inhabitantsAtAnaddressDTO, address, fireStation);

		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}

		return listOfInhabitants;

	}

	/**
	 * This URL returns a list of all households served by each selected stations.
	 * This list must group people by address. It should also include the name,
	 * phone number and age of residents, and list their medical history
	 * (medications, dosages and allergies) next to each name.
	 * 
	 * http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	 * 
	 * @param fireStations
	 * @return
	 * @throws DataNotFoundException
	 */
	public List<InfoStationDTO> getListOfInhabitantsForAStation(List<Integer> fireStations)
			throws DataNotFoundException {
		logger.info("create List of info by inhabitants for a list of stations : getListOfInhabitantsForAStation ");

		List<Person> personsAtAnAddress = new ArrayList<>();
		List<String> addresses = new ArrayList<>();
		List<InfoStationDTO> listOfInhabitants = new ArrayList<>();

		int age;

		int[] fireStation = fireStations.stream().mapToInt(Integer::intValue).toArray();
		for (int fs : fireStation) {
			// listOfInhabitants.add("fire station : " + fs);
			MedicalRecords medicalRecords;

			addresses = fireStationService.findAddressByFireStation(fs);
			if (!(addresses == null)) {
				for (String address : addresses) {
					List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress = new ArrayList<>();
					personsAtAnAddress = personService.findPersonsByAddress(address);

					for (Person p : personsAtAnAddress) {

						medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(),
								p.getFirstName());
						age = AgeCalculator.calculate(medicalRecords.getBirthdate());

						inhabitantsAtAnaddress.add(new InhabitantsAtAnaddressDTO(p.getLastName(), p.getFirstName(),
								p.getPhone(), age, medicalRecords.getMedications(), medicalRecords.getAllergies()));

					}
					listOfInhabitants.add(new InfoStationDTO(fs, address, inhabitantsAtAnaddress));
				}
			}
		}
		return listOfInhabitants;

	}

	/**
	 * 
	 * This url returns the name, address, age, email address and medical history
	 * (medication dosage, allergies) of the person with the name and first name
	 * entered. If other people have the same last name, they must all appear.
	 * 
	 * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	 * 
	 */
	public List<InfoForFirstAndLastNameDTO> getInformationsForAGivenPersonAndHisFamily(String firstName,
			String lastName) {
		logger.info("create List of info by person with given name : getInformationsForAGivenPersonAndHisFamily ");

		Person personWithTheGoodNameAndFirstName = new Person();
		List<Person> personsWiththeSameName;
		List<InfoForFirstAndLastNameDTO> infoForFirstAndLastName = new ArrayList<>();
		MedicalRecords medicalRecords;
		int age;

		try {
			// We could have exception on searching by name : data not found to manage
			personsWiththeSameName = personService.findPersonsByLastName(lastName);

			// First for the people with the good name AND firstName
			personWithTheGoodNameAndFirstName = personService.findPersonByLastNameAndFirstName(lastName, firstName);
			medicalRecords = medicalRecordsService.findMRByNameAndFirstName(lastName, firstName);
			age = AgeCalculator.calculate(medicalRecords.getBirthdate());

			infoForFirstAndLastName.add(new InfoForFirstAndLastNameDTO(lastName, firstName,
					personWithTheGoodNameAndFirstName.getAddress(), personWithTheGoodNameAndFirstName.getEmail(), age,
					medicalRecords.getMedications(), medicalRecords.getAllergies()));

			// Second the other people with the same name
			for (Person p : personsWiththeSameName) {
				if (!p.getFirstName().equals(firstName)) {

					medicalRecords = medicalRecordsService.findMRByNameAndFirstName(p.getLastName(), p.getFirstName());
					age = AgeCalculator.calculate(medicalRecords.getBirthdate());

					infoForFirstAndLastName
							.add(new InfoForFirstAndLastNameDTO(p.getLastName(), p.getFirstName(), p.getAddress(),
									p.getEmail(), age, medicalRecords.getMedications(), medicalRecords.getAllergies()));
				}

			}
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infoForFirstAndLastName;
	}

	/**
	 * Define the list of email to send emergency message
	 * 
	 * Endpoint from communityEmail?city=<city>
	 * 
	 * @param city
	 * @return the List<String> of email of inhabitants to allow emergency services
	 *         to send message
	 * @throws DataNotFoundException
	 * 
	 */
	public List<String> getEmailforPersonsInTheCity(String city) {
		logger.info("create List of email for the inhabitants of the city ");

		List<Person> personsInTheCity = new ArrayList<>();
		List<String> email = new ArrayList<>();

		try {
			personsInTheCity.addAll(personService.findPersonsByCity(city));

			email = personsInTheCity.stream().map(Person::getEmail).distinct().collect(Collectors.toList());
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;

	}

}
