package com.safetynet.alerts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.Person;

public class PersonRepositoryTest {

	private static PersonRepository personRepository;
	private static List<Person> persons = new ArrayList<>();

	@BeforeAll
	public static void initPersons() {
		persons.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		persons.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));
		persons.add(new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3"));
		persons.add(new Person("firstName4", "lastName4", "address1", "city2", "5678", "0173456486", "email4"));
		persons.add(new Person("firstName5", "lastName5", "address1", "city2", "5678", "0127446322", "email5"));

		personRepository = new PersonRepository(persons);
	}

	@Test
	public void addPersonTest() {
		// GIVEN
		Person newPerson = new Person("Annie", "Lechevalier", "8 rue loin", "VillePasLa", "7777", "000000000",
				"email.com");
		// WHEN
		personRepository.addPerson(newPerson);
		// THEN
		assertThat(persons.contains(newPerson));
	}

	@Test
	public void deletePersonTest() {
		// GIVEN
		Person oldPerson = new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1");
		// WHEN
		personRepository.deletePerson(oldPerson);
		// THEN
		assertThat(persons).doesNotContain(oldPerson);
	}

	@Test
	public void updatePersonTest() throws IOException {
		//Given : an updated person : new address and so on
		Person person = new Person("firstName1", "lastName1", "address1New", "city1New", "1234", "0123456789", "email1New");
		// when
		Person result = personRepository.updatePerson(person);
		// then
		assertThat(result).isEqualTo(person);

	}

	@Tag("findbyName")
	@Test
	public void findPersonByNameAndFirstName_withAnExistingPerson_ShouldReturnOK() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		Person result = personRepository.findPersonByNameAndFirstName("lastName1", "firstName1");
		// Then
		assertThat(persons).contains(result);
		assertThat(result).isNotNull();
	}

	@Tag("findbyName")
	@Test
	public void findPersonByNameAndFirstName_withAnNONExistingPerson_ShouldReturnNull() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		Person result = personRepository.findPersonByNameAndFirstName("lastName99", "firstName1");
		// Then
		assertThat(persons).doesNotContain(result);
		assertThat(result).isNull();
	}

	@Tag("findbyName")
	@Test
	public void findPersonByName_withAnExistingPerson_ShouldReturnOK() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		List<Person> result = personRepository.findPersonsByName("lastName1");
		// Then
		assertThat(persons).containsAll(result);
		assertThat(result).isNotNull();
	}

	@Tag("findbyName")
	@Test
	public void findPersonByName_withAnNONExistingPerson_ShouldReturnAnEmptyList() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		List<Person> result = personRepository.findPersonsByName("lastName99");
		// Then

		assertThat(result).isEmpty();
	}

	@Tag("findbyCity")
	@Test
	public void findPersonByCity_withAnExistingCity_ShouldReturnOK() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		List<Person> result = personRepository.findPersonsByCity("city1");
		// Then
		assertThat(persons).containsAll(result);
		assertThat(result).isNotNull();
	}

	@Tag("findbyCity")
	@Test
	public void findPersonByCity_withAnNONExistingCity_ShouldReturnAnEmptyList() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		List<Person> result = personRepository.findPersonsByCity("city99");
		// Then

		assertThat(result).isEmpty();
	}
	
	@Tag("findbyAddress")
	@Test
	public void findPersonByAddress_withAnExistingAddress_ShouldReturnOK() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		List<Person> result = personRepository.findPersonsByAddress("address1");
		// Then
		assertThat(persons).containsAll(result);
		assertThat(result).isNotNull();
	}

	@Tag("findbyAddress")
	@Test
	public void findPersonByAddresss_withAnNONExistingAddress_ShouldReturnAnEmptyList() throws IOException {
		// Given : the persons have already been loaded within the initPersons method
		// When
		List<Person> result = personRepository.findPersonsByAddress("address99");
		// Then

		assertThat(result).isEmpty();
	}

}
