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
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;

@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {
	
	private static List<FireStation> fireStations = new ArrayList<>();

	private static FireStation fireStationExist = new FireStation();
	private static FireStation fireStationNew = new FireStation();
	private static FireStation fireStationNew2 = new FireStation();
	private static FireStation fireStationKO = new FireStation();
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FireStationService fireStationService;

	@BeforeEach
	void setUp() throws Exception {
		
		fireStations.add(new FireStation("1509 Culver St", 3));
		fireStations.add(new FireStation("address2", 1));
		fireStations.add(new FireStation("address3", 2));
		fireStations.add(new FireStation("address4", 1));
		fireStations.add(new FireStation("address5", 2));

		fireStationExist = new FireStation("1509 Culver St", 3);
		fireStationNew = new FireStation("8 rue Neuve", 10);
		fireStationNew2 = new FireStation("9 rue Neuve", 2);
		fireStationKO = new FireStation("8 rue Bof", 0);

	}

	@Test
	void testGetFireStations() throws Exception {
		when(fireStationService.getFireStationsService()).thenReturn(fireStations);

		mockMvc.perform(get("/firestations")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].address").value("1509 Culver St"))
				.andExpect(jsonPath("$[2].station").value(2));
	}

	@Test
	void testAddFireStation() throws Exception {
		
		String message = "Fire Station : " + fireStationNew.getAddress() + " - station number : "
				+ fireStationNew.getStation() + " has been added";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.addFireStation(fireStationNew)).thenReturn(message);

		mockMvc.perform(
				post("/firestation").content(om.writeValueAsString(fireStationNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
		
	}
	
	@Test
	void testAddFireStation_withAnExistingPerson() throws Exception {
		String message = "A FireStation already exists at the address " + fireStationExist.getAddress();
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.addFireStation(fireStationExist)).thenThrow(AlreadyExistsException.class);

		mockMvc.perform(
				post("/firestation").content(om.writeValueAsString(fireStationExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}
	
	@Test
	void testAddFireStation_withAnIncorrectFireStation() throws Exception {
		String message = "Address Empty or station null : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.addFireStation(fireStationKO)).thenReturn(message);

		mockMvc.perform(
				post("/firestation").content(om.writeValueAsString(fireStationKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testDeleteFireStation_withAnExistingFireStation() throws Exception {

		String message = "Fire Station : " + fireStationExist.getAddress() + " - station number : "
				+ fireStationExist.getStation() + " has been deleted";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.deleteFireStation(fireStationExist)).thenReturn(message);

		mockMvc.perform(
				delete("/firestation").content(om.writeValueAsString(fireStationExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testDeleteFireStation_UnknownFireStation() throws Exception {
		String message = "No Fire station Deleted, no such station found in the database";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.deleteFireStation(fireStationNew)).thenThrow(DataNotFoundException.class);

		mockMvc.perform(
				delete("/firestation").content(om.writeValueAsString(fireStationNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteFireStation_withAnIncorrectFS() throws Exception {
		String message = "Address Empty or station null : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.deleteFireStation(fireStationKO)).thenReturn(message);

		mockMvc.perform(
				delete("/firestation").content(om.writeValueAsString(fireStationKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}
	@Test
	void testUpdateFireStation_withAnExistingFireStation() throws Exception {

		String message = "Fire Station : " + fireStationExist.getAddress() + " - station number : "
				+ fireStationExist.getStation() + " has been updated";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.updateFireStation(fireStationExist)).thenReturn(message);

		mockMvc.perform(
				put("/firestation").content(om.writeValueAsString(fireStationExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testUpdateFireStation_UnknownFireStation() throws Exception {
		String message = "No Fire station updated, no such station found in the database";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.updateFireStation(fireStationNew2)).thenThrow(DataNotFoundException.class);

		mockMvc.perform(
				put("/firestation").content(om.writeValueAsString(fireStationNew2)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateFireStation_withAnIncorrectFS() throws Exception {
		String message = "Address Empty or station null : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		when(fireStationService.updateFireStation(fireStationKO)).thenReturn(message);

		mockMvc.perform(
				put("/firestation").content(om.writeValueAsString(fireStationKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	
}
