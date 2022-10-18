package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.model.Person;

@ExtendWith(MockitoExtension.class)
class AlertsServiceTest {

	private static List<Person> persons = new ArrayList<>();
	private static List<FireStation> fireStations = new ArrayList<>();
	private static List<MedicalRecords> medicalRecords = new ArrayList<>();

	@Mock
	private static PersonService personService;
	@Mock
	private static FireStationService fireStationService;
	@Mock
	private static MedicalRecordsService medicalRecordService;

	@InjectMocks
	private static AlertsService alertsService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		persons.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		persons.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));
		persons.add(new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3"));
		persons.add(new Person("firstName4", "lastName4", "address4", "city2", "5678", "0173456486", "email4"));
		persons.add(new Person("firstName5", "lastName5", "address3", "city2", "5678", "0127446322", "email5"));

		fireStations.add(new FireStation("address1", 1));
		fireStations.add(new FireStation("address2", 1));
		fireStations.add(new FireStation("address3", 2));
		fireStations.add(new FireStation("address4", 1));

		medicalRecords.add(new MedicalRecords("firstName1", "lastName1", "09/25/1971", List.of("doliprane 500mg"),
				List.of("rhume Des Foins")));
		medicalRecords.add(new MedicalRecords("firstName2", "lastName1", "09/12/2005", List.of("pivalone 60"),
				List.of("rhume Des Foins", "acariens")));
		medicalRecords.add(new MedicalRecords("firstName3", "lastName3", "12/25/1965", List.of(), List.of()));
		medicalRecords.add(new MedicalRecords("firstName4", "lastName4", "12/25/1932",
				List.of("doliprane 1000mg", "cardio 200"), List.of("iode")));
		medicalRecords.add(new MedicalRecords("firstName5", "lastName5", "12/25/1948",
				List.of("doliprane 1000mg", "tramadol 100"), List.of()));

	}

	@Test
	void testGetPhoneforPersonsCoveredByStation() throws DataNotFoundException {
		// Given
		List<String> phone = new ArrayList<>();
		phone.add("0127446322");
		when(fireStationService.findAddressByFireStation(anyInt()))
				.thenReturn(new ArrayList<String>(List.of("adress3")));
		when(personService.findPersonsByAddress(anyString()))
				.thenReturn(new ArrayList<Person>(List.of(persons.get(4))));

		// When
		List<String> result = alertsService.getPhoneforPersonsCoveredByStation(2);

		// Then
		assertThat(result).containsAll(phone);
		verify(personService).findPersonsByAddress(anyString());
		verify(fireStationService).findAddressByFireStation(2);
	}

	@Test
	void testGetListOfPersonsCoveredByStation() throws DataNotFoundException {
		// given
		List<PersonsCoveredDTO> personTotTest = new ArrayList<>();
		personTotTest.add(new PersonsCoveredDTO("firstName5", "lastName5", "address3", "0127446322"));
		when(fireStationService.findAddressByFireStation(anyInt()))
				.thenReturn(new ArrayList<String>(List.of("adress3")));
		when(personService.findPersonsByAddress(anyString()))
				.thenReturn(new ArrayList<Person>(List.of(persons.get(4))));
		when(medicalRecordService.findMRByNameAndFirstName(anyString(), anyString())).thenReturn(new MedicalRecords(
				"firstName5", "lastName5", "12/25/1948", List.of("doliprane 1000mg", "tramadol 100"), List.of()));

		// when
		InhabitantsCoveredDTO result = alertsService.getListOfPersonsCoveredByStation(2);

		// then
		assertThat(result.getNbAdults()).isEqualTo(1);
		assertThat(result.getNbChildren()).isEqualTo(0);
		assertThat(result.getPersonsCoveredDTO()).isEqualTo(personTotTest);
	}

	@Test
	void testGetListOfChildrenAtAnAdress() throws DataNotFoundException {
		// given
		List<NameFirstnameAndAgeDTO> listOfChildren = new ArrayList<>();
		listOfChildren.add(new NameFirstnameAndAgeDTO("firstName2", "lastName1", 17));
		List<NameAndFirstNameDTO> listOfAdults = new ArrayList<>();
		listOfAdults.add(new NameAndFirstNameDTO("lastName1", "firstName1"));

		when(personService.findPersonsByAddress(anyString()))
				.thenReturn(new ArrayList<Person>(List.of(persons.get(0), persons.get(1))));
		when(medicalRecordService.findMRByNameAndFirstName("lastName1", "firstName1")).thenReturn(new MedicalRecords(
				"firstName1", "lastName1", "09/25/1971", List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		when(medicalRecordService.findMRByNameAndFirstName("lastName1", "firstName2"))
				.thenReturn(new MedicalRecords("firstName2", "lastName1", "09/12/2005", List.of("pivalone 60"),
						List.of("rhume Des Foins", "acariens")));

		// when
		ListOfChildAlertDTO result = alertsService.getListOfChildrenAtAnAdress("address1");

		// then
		assertThat(result.getListOfChildren()).isEqualTo(listOfChildren);
		assertThat(result.getListOfAdults()).isEqualTo(listOfAdults);
		verify(personService).findPersonsByAddress("address1");
		verify(medicalRecordService, times(2)).findMRByNameAndFirstName(anyString(), anyString());
	}

	@Test
	void testGetListOfInhabitantsAtAnAddress() throws DataNotFoundException {
		// given

		List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress = new ArrayList<>();
		inhabitantsAtAnaddress.add(new InhabitantsAtAnaddressDTO("lastName1", "firstName1", "0123456789", 51,
				List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		inhabitantsAtAnaddress.add(new InhabitantsAtAnaddressDTO("lastName1", "firstName2", "0123456789", 17,
				List.of("pivalone 60"), List.of("rhume Des Foins", "acariens")));

		String address = "address1";
		int fireStationNumber = 1;
		when(fireStationService.findTheNumberOfFirestationByAddress(anyString())).thenReturn(fireStationNumber);
		when(personService.findPersonsByAddress(anyString()))
				.thenReturn(new ArrayList<Person>(List.of(persons.get(0), persons.get(1))));
		when(medicalRecordService.findMRByNameAndFirstName("lastName1", "firstName1")).thenReturn(new MedicalRecords(
				"firstName1", "lastName1", "09/25/1971", List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		when(medicalRecordService.findMRByNameAndFirstName("lastName1", "firstName2"))
				.thenReturn(new MedicalRecords("firstName2", "lastName1", "09/12/2005", List.of("pivalone 60"),
						List.of("rhume Des Foins", "acariens")));

		// when
		InfoAddressDTO result = alertsService.getListOfInhabitantsAtAnAddress("address1");

		// then
		assertThat(result.getFireStationNumber()).isEqualTo(1);
		assertThat(result.getAddress()).isEqualTo(address);
		assertThat(result.getInhabitantsAtAnaddress()).isEqualTo(inhabitantsAtAnaddress);
		verify(personService).findPersonsByAddress(address);
		verify(medicalRecordService, times(2)).findMRByNameAndFirstName(anyString(), anyString());
		verify(fireStationService).findTheNumberOfFirestationByAddress(address);

	}

	@Test
	void testgetListOfInhabitantsForAStation() throws DataNotFoundException {

		// given
		List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress = new ArrayList<>();
		inhabitantsAtAnaddress.add(new InhabitantsAtAnaddressDTO("lastName5", "firstName5", "0127446322", 73,
				List.of("doliprane 1000mg", "tramadol 100"), List.of()));

		when(fireStationService.findAddressByFireStation(anyInt()))
				.thenReturn(new ArrayList<String>(List.of("address3")));
		when(personService.findPersonsByAddress(anyString()))
				.thenReturn(new ArrayList<Person>(List.of(persons.get(4))));
		when(medicalRecordService.findMRByNameAndFirstName(anyString(), anyString())).thenReturn(new MedicalRecords(
				"firstName5", "lastName5", "12/25/1948", List.of("doliprane 1000mg", "tramadol 100"), List.of()));

		// when
		List<InfoStationDTO> result = alertsService.getListOfInhabitantsForAStation(List.of(2));
		// then
		assertThat(result.get(0).getAddress()).isEqualTo("address3");
		assertThat(result.get(0).getFireStationNumber()).isEqualTo(2);
		assertThat(result.get(0).getInhabitantsAtAnaddressDTO()).isEqualTo(inhabitantsAtAnaddress);

	}

	@Test
	void testgetInformationsForAGivenPersonAndHisFamily() throws DataNotFoundException {
		// given
		List<Person> family = new ArrayList<>();
		family.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));

		when(personService.findPersonByLastNameAndFirstName(anyString(), anyString()))
				.thenReturn(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		when(personService.findPersonsByLastName(anyString())).thenReturn(family);

		when(medicalRecordService.findMRByNameAndFirstName("lastName1", "firstName1")).thenReturn(new MedicalRecords(
				"firstName1", "lastName1", "09/25/1971", List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		when(medicalRecordService.findMRByNameAndFirstName("lastName1", "firstName2"))
				.thenReturn(new MedicalRecords("firstName2", "lastName1", "09/12/2005", List.of("pivalone 60"),
						List.of("rhume Des Foins", "acariens")));

		// when
		List<InfoForFirstAndLastNameDTO> result = alertsService.getInformationsForAGivenPersonAndHisFamily("firstName1",
				"lastName1");

		// then
		// la personne recherchee exactement nom et prenom
		assertThat(result.get(0).getMedications()).isEqualTo(List.of("doliprane 500mg"));
		assertThat(result.get(1).getAllergies()).isEqualTo(List.of("rhume Des Foins", "acariens"));

		verify(personService).findPersonByLastNameAndFirstName("lastName1", "firstName1");
		verify(medicalRecordService, times(2)).findMRByNameAndFirstName(anyString(), anyString());

	}
	
	
	@Test
	void testgetEmailforPersonsInTheCity() throws DataNotFoundException {
		
		
		//given
		
		List<Person> inhabitants = new ArrayList<>();
		inhabitants.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		inhabitants.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));
		inhabitants.add(new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3"));

		when(personService.findPersonsByCity(anyString())).thenReturn(inhabitants);
		
		//when
		List<String> result = alertsService.getEmailforPersonsInTheCity("city1");
		//then
		
		assertThat(result).isEqualTo(List.of("email1", "email2", "email3"));
		
	}
	
}
