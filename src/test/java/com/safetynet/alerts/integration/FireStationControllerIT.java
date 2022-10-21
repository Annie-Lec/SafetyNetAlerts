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

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {
	
	private static FireStation fireStationExist = new FireStation();
	private static FireStation fireStationNew = new FireStation();
	private static FireStation fireStationNew2 = new FireStation();
	private static FireStation fireStationKO = new FireStation();
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReadDataSourceFromJson readDataSource;
	

	@BeforeEach
	void init() throws IOException {

		readDataSource.readData();
		
		fireStationExist = new FireStation("1509 Culver St", 3);
		fireStationNew = new FireStation("8 rue Neuve", 10);
		fireStationNew2 = new FireStation("9 rue Neuve", 2);
		fireStationKO = new FireStation("8 rue Bof", 0);
		
	}
	
	@Test
	void testGetFireStations() throws Exception {

		mockMvc.perform(get("/firestations")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].address").value("29 15th St"))
				.andExpect(jsonPath("$[0].station").value(2));
	}

	@Test
	void testAddFireStation() throws Exception {
		
		String message = "Fire Station : " + fireStationNew.getAddress() + " - station number : "
				+ fireStationNew.getStation() + " has been added";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/firestation").content(om.writeValueAsString(fireStationNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
		
	}
	
	@Test
	void testAddFireStation_withAnExistingPerson() throws Exception {
		String message = "A FireStation already exists at the address " + fireStationExist.getAddress();
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/firestation").content(om.writeValueAsString(fireStationExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}
	
	@Test
	void testAddFireStation_withAnIncorrectFireStation() throws Exception {
		String message = "Address Empty or station null : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				post("/firestation").content(om.writeValueAsString(fireStationKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}

	@Test
	void testDeleteFireStation_withAnExistingFireStation() throws Exception {

		String message = "Fire Station : " + fireStationExist.getAddress() + " - station number : "
				+ fireStationExist.getStation() + " has been deleted";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/firestation").content(om.writeValueAsString(fireStationExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testDeleteFireStation_UnknownFireStation() throws Exception {
		String message = "No Fire station Deleted, no such station found in the database";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/firestation").content(om.writeValueAsString(fireStationNew)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}

	@Test
	void testDeleteFireStation_withAnIncorrectFS() throws Exception {
		String message = "Address Empty or station null : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				delete("/firestation").content(om.writeValueAsString(fireStationKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}
	@Test
	void testUpdateFireStation_withAnExistingFireStation() throws Exception {

		String message = "Fire Station : " + fireStationExist.getAddress() + " - station number : "
				+ fireStationExist.getStation() + " has been updated";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/firestation").content(om.writeValueAsString(fireStationExist)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(message));
	}

	@Test
	void testUpdateFireStation_UnknownFireStation() throws Exception {
		String message = "No Fire station updated, no such station found in the database";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/firestation").content(om.writeValueAsString(fireStationNew2)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdateFireStation_withAnIncorrectFS() throws Exception {
		String message = "Address Empty or station null : unable to add/delete/update";
		ObjectMapper om = new ObjectMapper();

		mockMvc.perform(
				put("/firestation").content(om.writeValueAsString(fireStationKO)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(message)).andExpect(status().isBadRequest());

	}



}
