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
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.service.MedicalRecordsService;

@WebMvcTest(controllers = MedicalRecordsController.class)
class MedicalRecordsControllerTest {

	private static List<MedicalRecords> medicalRecords = new ArrayList<>();

	private static MedicalRecords MRExist = new MedicalRecords();
	private static MedicalRecords MRNew = new MedicalRecords();
	private static MedicalRecords MRKO = new MedicalRecords();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MedicalRecordsService medicalRecordsService;

	@BeforeEach
	void setUp() throws Exception {

		medicalRecords.add(new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"),
				List.of("rhume Des Foins")));
		medicalRecords.add(new MedicalRecords("firstName2", "lastName2", "25/12/20005", List.of("pivalone 60"),
				List.of("rhume Des Foins", "acariens")));
		medicalRecords.add(new MedicalRecords("firstName3", "lastName3", "25/12/1965", List.of(), List.of()));
		medicalRecords.add(new MedicalRecords("firstName4", "lastName4", "25/12/1932",
				List.of("doliprane 1000mg", "cardio 200"), List.of("iode")));
		medicalRecords.add(new MedicalRecords("firstName5", "lastName5", "25/12/1948",
				List.of("doliprane 1000mg", "tramadol 100"), List.of()));

		MRExist = new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"),
				List.of("rhume Des Foins"));
		MRNew = new MedicalRecords("Nouveau", "prenom", "31/12/1931", List.of("dolirhume 100mg"),
				List.of("allergie aux poils de chat"));
		MRKO = new MedicalRecords("", "MRKOCarNomEmpty", "25/12/1951", List.of("antalgy 50mg"), List.of());

	}

	@Test
	void testGetMedicalRecords() throws Exception {
		when(medicalRecordsService.getMedicalRecords()).thenReturn(medicalRecords);

		mockMvc.perform(get("/medicalrecords")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[3].firstName").value("firstName4"))
				.andExpect(jsonPath("$[0].medications[0]").value("doliprane 500mg"));

	}

	@Test
	void testAddMedicalRecords() throws Exception {

		String message = "MedicalRecords for : " + MRNew.getLastName() + " - " + MRNew.getFirstName()
				+ " has been added";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.addMedicalRecords(MRNew)).thenReturn(message);

		mockMvc.perform(
				post("/medicalrecord").content(om.writeValueAsString(MRNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));

	}

	@Test
	void testAddMedicalRecords_withAnExistingPerson() throws Exception {

		String message = "A person already exists with this name and firstName";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.addMedicalRecords(MRExist)).thenThrow(AlreadyExistsException.class);

		mockMvc.perform(
				post("/medicalrecord").content(om.writeValueAsString(MRExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testAddMedicalRecords_withAnIncorrectPerson() throws Exception {

		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.addMedicalRecords(MRKO)).thenReturn(message);

		mockMvc.perform(
				post("/medicalrecord").content(om.writeValueAsString(MRKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string(message));

	}

	@Test
	void testDeleteMedicalRecords_withAnExistingPerson() throws Exception {

		String message = "MedicalRecords for : " + MRExist.getLastName() + " - " + MRExist.getFirstName()
				+ " has been deleted";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.deleteMedicalRecords(MRExist)).thenReturn(message);

		mockMvc.perform(delete("/medicalrecord").content(om.writeValueAsString(MRExist))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(message));

	}

	@Test
	void testDeleteMedicalRecords_UnknownPerson() throws Exception {
		String message = "No MedicalRecords with this name and firstName could be found : unable to delete";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.deleteMedicalRecords(MRNew)).thenThrow(DataNotFoundException.class);

		mockMvc.perform(
				delete("/medicalrecord").content(om.writeValueAsString(MRNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteMedicalRecords_withAnIncorrectPerson() throws Exception {

		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.deleteMedicalRecords(MRKO)).thenReturn(message);

		mockMvc.perform(
				delete("/medicalrecord").content(om.writeValueAsString(MRKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().string(message));

	}

	@Test
	void testupdateMedicalRecords_withAnExistingPerson() throws Exception {

		String message = "MedicalRecords for : " + MRExist.getLastName() + " - " + MRExist.getFirstName()
				+ " has been updated";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.updateMedicalRecords(MRExist)).thenReturn(message);

		mockMvc.perform(
				put("/medicalrecord").content(om.writeValueAsString(MRExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));

	}

	@Test
	void testupdateMedicalRecords_UnknownPerson() throws Exception {
		String message = "No MedicalRecords with this name and firstName could be found : unable to update";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.updateMedicalRecords(MRNew)).thenThrow(DataNotFoundException.class);

		mockMvc.perform(
				put("/medicalrecord").content(om.writeValueAsString(MRNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateMedicalRecords_withAnIncorrectPerson() throws Exception {

		String message = "Name or FirstName Empty : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(medicalRecordsService.updateMedicalRecords(MRKO)).thenReturn(message);

		mockMvc.perform(
				put("/medicalrecord").content(om.writeValueAsString(MRKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

}
