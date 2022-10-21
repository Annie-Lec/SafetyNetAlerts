package com.safetynet.alerts.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

	private static Person personExist = new Person();
	private static Person personNew = new Person();
	private static Person personKO = new Person();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReadDataSourceFromJson readDataSource;

	@BeforeEach
	void init() throws IOException {

		readDataSource.readData();

		personExist = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com");
		personNew = new Person("Nouveauperson", "prenom", "address2", "city1", "7777", "0177777777", "email7");
		personKO = new Person("PersonKOsansprenom", "", "address2", "city1", "0002", "0100000007", "email0");

	}

	@Test
	void testGetPersons() throws Exception {
		mockMvc.perform(get("/persons")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[2].email").value("tenz@email.com"));
	}

	@Test
	void testAddPerson() throws Exception {

		String message = "Person : " + personNew.getLastName() + " - " + personNew.getFirstName() + " has been added";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/person").content(om.writeValueAsString(personNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testAddPerson_withAnExistingPerson() throws Exception {
		String message = "A person already exists with this name and firstName";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/person").content(om.writeValueAsString(personExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testAddPerson_withAnIncorrectPerson() throws Exception {
		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/person").content(om.writeValueAsString(personKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testDeletePerson_withAnExistingPerson() throws Exception {

		String message = "Person : " + personExist.getLastName() + " - " + personExist.getFirstName()
				+ " has been deleted";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/person").content(om.writeValueAsString(personExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testDeletePerson_UnknownPerson() throws Exception {
		String message = "No person with this name and firstName could be found : unable to delete";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/person").content(om.writeValueAsString(personNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeletePerson_withAnIncorrectPerson() throws Exception {
		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/person").content(om.writeValueAsString(personKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testUpdatePerson_withAnExistingPerson() throws Exception {

		String message = "Person : " + personExist.getLastName() + " - " + personExist.getFirstName()
				+ " has been updated";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/person").content(om.writeValueAsString(personExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testUpddatePerson_withAnUnknownPerson() throws Exception {

		String message = "No person with this name and firstName could be found : unable to update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/person").content(om.writeValueAsString(personNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdatePerson_withAnIncorrectPerson() throws Exception {
		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(put("/person").content(om.writeValueAsString(personKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

}
