package com.safetynet.alerts.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordsControllerIT {
	
	private static MedicalRecords MRExist = new MedicalRecords();
	private static MedicalRecords MRNew = new MedicalRecords();
	private static MedicalRecords MRKO = new MedicalRecords();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReadDataSourceFromJson readDataSource;

	@BeforeEach
	void init() throws IOException {

		readDataSource.readData();

		MRExist = new MedicalRecords("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"),
				List.of("nillacilan"));
		MRNew = new MedicalRecords("Nouveau", "prenom", "12/31/1931", List.of("dolirhume 100mg"),
				List.of("allergie aux poils de chat"));
		MRKO = new MedicalRecords("", "MRKOCarNomEmpty", "12/25/1951", List.of("antalgy 50mg"), List.of());

	}

	
	@Test
	void testGetMedicalRecords() throws Exception {
	
		mockMvc.perform(get("/medicalrecords")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].firstName").value("John"))
				.andExpect(jsonPath("$[0].medications[0]").value("aznol:350mg"));

	}

	@Test
	void testAddMedicalRecords() throws Exception {

		String message = "MedicalRecords for : " + MRNew.getLastName() + " - " + MRNew.getFirstName()
				+ " has been added";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/medicalrecord").content(om.writeValueAsString(MRNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));

	}

	@Test
	void testAddMedicalRecords_withAnExistingPerson() throws Exception {

		String message = "A person already exists with this name and firstName";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/medicalrecord").content(om.writeValueAsString(MRExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testAddMedicalRecords_withAnIncorrectPerson() throws Exception {

		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/medicalrecord").content(om.writeValueAsString(MRKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string(message));

	}

	@Test
	void testDeleteMedicalRecords_withAnExistingPerson() throws Exception {

		String message = "MedicalRecords for : " + MRExist.getLastName() + " - " + MRExist.getFirstName()
				+ " has been deleted";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(delete("/medicalrecord").content(om.writeValueAsString(MRExist))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(message));

	}

	@Test
	void testDeleteMedicalRecords_UnknownPerson() throws Exception {
		String message = "No MedicalRecords with this name and firstName could be found : unable to delete";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/medicalrecord").content(om.writeValueAsString(MRNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteMedicalRecords_withAnIncorrectPerson() throws Exception {

		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/medicalrecord").content(om.writeValueAsString(MRKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string(message));

	}

	@Test
	void testupdateMedicalRecords_withAnExistingPerson() throws Exception {

		String message = "MedicalRecords for : " + MRExist.getLastName() + " - " + MRExist.getFirstName()
				+ " has been updated";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/medicalrecord").content(om.writeValueAsString(MRExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));

	}

	@Test
	void testupdateMedicalRecords_UnknownPerson() throws Exception {
		String message = "No MedicalRecords with this name and firstName could be found : unable to update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/medicalrecord").content(om.writeValueAsString(MRNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateMedicalRecords_withAnIncorrectPerson() throws Exception {

		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

			mockMvc.perform(
				put("/medicalrecord").content(om.writeValueAsString(MRKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

}