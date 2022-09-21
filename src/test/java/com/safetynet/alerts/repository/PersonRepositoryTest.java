package com.safetynet.alerts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
		//GIVEN
		Person newPerson = new Person("Annie", "Lechevalier", "8 rue loin", "VillePasLa", "7777", "000000000", "email.com");
		//WHEN
		personRepository.addPerson(newPerson);
		//THEN
		assertThat(persons.contains(newPerson));
	}
	
	@Test
	public void deletePersonTest() {
		//GIVEN
		Person oldPerson = new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1");
		//WHEN
		personRepository.deletePerson(oldPerson);
		//THEN
		assertThat(persons).doesNotContain(oldPerson);
	}
	
	@Test
	public void updatePersonTest() {
		//persons.stream().filter(null)
	}


}
