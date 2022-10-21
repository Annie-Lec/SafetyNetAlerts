package com.safetynet.alerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

	private static List<Person> persons = new ArrayList<>();
	private static Person personExist = new Person();
	private static Person personNew = new Person();
	private static Person personKO = new Person();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService personService;

	@BeforeEach
	void setUpBeforeClass() throws Exception {

		persons.add(new Person("firstName1", "lastName1", "address1", "city1", "1234", "0123456789", "email1"));
		persons.add(new Person("firstName2", "lastName1", "address1", "city1", "1234", "0123456789", "email2"));
		persons.add(new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3"));

		personExist = new Person("firstName3", "lastName3", "address2", "city1", "1234", "0123456789", "email3");
		personNew = new Person("Nouveauperson", "prenom", "address2", "city1", "7777", "0177777777", "email7");
		personKO = new Person("PersonKOsansprenom", "", "address2", "city1", "0002", "0100000007", "email0");

	}

	@Test
	void testGetPersons() throws Exception {

		when(personService.getPersonsService()).thenReturn(persons);

		mockMvc.perform(get("/persons")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].firstName").value("firstName1"))
				.andExpect(jsonPath("$[2].email").value("email3"));

	}

	@Test
	void testAddPerson() throws Exception {

		String message = "Person : " + personNew.getLastName() + " - " + personNew.getFirstName() + " has been added";
		ObjectMapper om = new ObjectMapper();

		when(personService.addPerson(personNew)).thenReturn(message);

		mockMvc.perform(
				post("/person").content(om.writeValueAsString(personNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testAddPerson_withAnExistingPerson() throws Exception {
		String message = "A person already exists with this name and firstName";
		ObjectMapper om = new ObjectMapper();

		when(personService.addPerson(personExist)).thenThrow(AlreadyExistsException.class);

		mockMvc.perform(
				post("/person").content(om.writeValueAsString(personExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testAddPerson_withAnIncorrectPerson() throws Exception {
		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(personService.addPerson(personKO)).thenReturn(message);

		mockMvc.perform(
				post("/person").content(om.writeValueAsString(personKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testDeletePerson_withAnExistingPerson() throws Exception {

		String message = "Person : " + personExist.getLastName() + " - " + personExist.getFirstName()
				+ " has been deleted";
		ObjectMapper om = new ObjectMapper();

		when(personService.deletePerson(personExist)).thenReturn(message);

		mockMvc.perform(
				delete("/person").content(om.writeValueAsString(personExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testDeletePerson_UnknownPerson() throws Exception {
		String message = "No person with this name and firstName could be found : unable to delete";
		ObjectMapper om = new ObjectMapper();

		when(personService.deletePerson(personNew)).thenThrow(DataNotFoundException.class);

		mockMvc.perform(
				delete("/person").content(om.writeValueAsString(personNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeletePerson_withAnIncorrectPerson() throws Exception {
		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(personService.deletePerson(personKO)).thenReturn(message);

		mockMvc.perform(
				delete("/person").content(om.writeValueAsString(personKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testUpdatePerson_withAnExistingPerson() throws Exception {

		String message = "Person : " + personExist.getLastName() + " - " + personExist.getFirstName()
				+ " has been updated";
		ObjectMapper om = new ObjectMapper();

		when(personService.updatePerson(personExist)).thenReturn(message);

		mockMvc.perform(
				put("/person").content(om.writeValueAsString(personExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testUpddatePerson_withAnUnknownPerson() throws Exception {

		String message = "No person with this name and firstName could be found : unable to update";
		ObjectMapper om = new ObjectMapper();

		when(personService.updatePerson(personNew)).thenThrow(DataNotFoundException.class);

		mockMvc.perform(
				put("/person").content(om.writeValueAsString(personNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdatePerson_withAnIncorrectPerson() throws Exception {
		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(personService.updatePerson(personKO)).thenReturn(message);

		mockMvc.perform(put("/person").content(om.writeValueAsString(personKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

}
