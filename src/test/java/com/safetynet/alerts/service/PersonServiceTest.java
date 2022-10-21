package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	private static List<Person> persons = new ArrayList<>();
	private static List<Person> persons1 = new ArrayList<>();

	private static Person personExist = new Person();
	private static Person personNew = new Person();
	private static Person personKO = new Person();

	@InjectMocks
	private static PersonService personService;

	@Mock
	private static PersonRepository personRepositoryMock;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		persons.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		persons.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));
		persons.add(new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3"));
		persons.add(new Person("firstName4", "lastName4", "address1", "city2", "5678", "0173456486", "email4"));
		persons.add(new Person("firstName5", "lastName5", "address1", "city2", "5678", "0127446322", "email5"));

		persons1.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		persons1.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));

		personExist = new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3");
		personNew = new Person("Nouveauperson", "prenom", "address2", "city1", "7777", "0177777777", "email7");
		personKO = new Person("PersonKOsansprenom", "", "address2", "city1", "0002", "0100000007", "email0");

	}

	@Test
	void testFindPersonsByAddress_withAnExistingAddress() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonsByAddress(anyString())).thenReturn(persons1);
		// when
		List<Person> result = personService.findPersonsByAddress("address1");
		// Then
		assertThat(result).isEqualTo(persons1);
		verify(personRepositoryMock, times(1)).findPersonsByAddress(anyString());

	}

	@Test
	void testFindPersonsByAddress_withAnUnknownAddress() throws DataNotFoundException {
		// given
		String addressBidon = "8 rue Bidon";
		when(personRepositoryMock.findPersonsByAddress(addressBidon)).thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.findPersonsByAddress(addressBidon));
	}

	@Test
	void testFindPersonsByLastName_withAnExistingLastName() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonsByName(anyString())).thenReturn(persons1);
		// when
		List<Person> result = personService.findPersonsByLastName("lastName1");
		// Then
		assertThat(result).isEqualTo(persons1);
		verify(personRepositoryMock, times(1)).findPersonsByName(anyString());

	}

	@Test
	void testFindPersonsByLastName_withAnUnknownName() throws DataNotFoundException {
		// given
		String nameBidon = "Bidon";
		when(personRepositoryMock.findPersonsByName(nameBidon)).thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.findPersonsByLastName(nameBidon));
	}

	@Test
	void testFindPersonByLastNameAndFirstName_withAnExistingLastName() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(anyString(), anyString())).thenReturn(personExist);
		// when
		Person result = personService.findPersonByLastNameAndFirstName("lastName3", "firstName3");
		// Then
		assertThat(result).isEqualTo(personExist);
		verify(personRepositoryMock, times(1)).findPersonByNameAndFirstName("lastName3", "firstName3");

	}

	@Test
	void testFindPersonByLastNameAndFirstName_withAnUnknownName() throws DataNotFoundException {
		// given
		String nameBidon = "Bidon";
		String prenomBidon = "Antoine";
		when(personRepositoryMock.findPersonByNameAndFirstName(nameBidon, prenomBidon)).thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class,
				() -> personService.findPersonByLastNameAndFirstName(nameBidon, prenomBidon));
	}

	@Test
	void testFindPersonsByCity_withAnExistingCity() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonsByCity(anyString())).thenReturn(persons1);
		// when
		List<Person> result = personService.findPersonsByCity("City1");
		// Then
		assertThat(result).isEqualTo(persons1);
		verify(personRepositoryMock, times(1)).findPersonsByCity("City1");
	}

	@Test
	void testFindPersonsByCity_withAnUnknownCity() throws DataNotFoundException {
		// given
		String nameBidon = "Bidon";
		when(personRepositoryMock.findPersonsByCity(nameBidon)).thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.findPersonsByCity(nameBidon));
	}

	@Test
	void testGetPersonsService() {
		// given
		when(personRepositoryMock.findAllThePersons()).thenReturn(persons);
		// when
		List<Person> result = personService.getPersonsService();
		// then
		assertThat(result).isEqualTo(persons);
	}

	@Test
	void testAddPerson_withANewPerson_MustBeOK() throws AlreadyExistsException, DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(personNew.getLastName(), personNew.getFirstName()))
				.thenReturn(null);
		// when
		String result = personService.addPerson(personNew);
		// then
		assertThat(result).isEqualTo(
				"Person : " + personNew.getLastName() + " - " + personNew.getFirstName() + " has been added");

	}

	@Test
	void testAddPerson_withAnOldPerson_MustBeKO() throws AlreadyExistsException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(personExist.getLastName(), personExist.getFirstName()))
				.thenReturn(personExist);
		// Then
		assertThrows(AlreadyExistsException.class, () -> personService.addPerson(personExist));
	}

	@Test
	void testAddPerson_WithABadNameOrFirstName() throws AlreadyExistsException, DataNotFoundException {
	
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.addPerson(personKO));

	}

	@Test
	void testDeletePerson_WithABadNameOrFirstName() throws DataNotFoundException {
	
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.deletePerson(personKO));

	}

	@Tag("deletePerson")
	@Test
	void testDeletePerson_withAnNewPerson_MustBeKO() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(personNew.getLastName(), personNew.getFirstName()))
				.thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.deletePerson(personNew));
	}

	@Tag("deletePerson")
	@Test
	void testDeletePerson_withAnOldPerson_MustBeOK() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(personExist.getLastName(), personExist.getFirstName()))
				.thenReturn(personExist);
		// when
		String result = personService.deletePerson(personExist);
		// then
		assertThat(result).isEqualTo(
				"Person : " + personExist.getLastName() + " - " + personExist.getFirstName() + " has been deleted");
	}

	@Tag("updatePerson")
	@Test
	void testUpdatePerson_withAnNewPerson_MustBeKO() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(personNew.getLastName(), personNew.getFirstName()))
				.thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.updatePerson(personNew));
	}

	@Tag("updatePerson")
	@Test
	void testUpdatePerson_withAnOldPerson_MustBeOK() throws DataNotFoundException {
		// given
		when(personRepositoryMock.findPersonByNameAndFirstName(personExist.getLastName(), personExist.getFirstName()))
				.thenReturn(personExist);
		// when
		String result = personService.updatePerson(personExist);
		// then
		assertThat(result).isEqualTo(
				"Person : " + personExist.getLastName() + " - " + personExist.getFirstName() + " has been updated");
	}
	
	@Test
	void testUpdatePerson_WithABadNameOrFirstName() throws DataNotFoundException {
	
		// Then
		assertThrows(DataNotFoundException.class, () -> personService.updatePerson(personKO));

	}

}
