package com.safetynet.alerts.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootTest
@AutoConfigureMockMvc
public class AlertsControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReadDataSourceFromJson readDataSource;

	@BeforeEach
	void init() throws IOException {

		readDataSource.readData();
	}

	@Test
	void testSMS_ok() throws Exception {

		mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0]").value("841-874-6512")).andExpect(jsonPath("$[1]").value("841-874-8547"));
	}

	@Test
	void testSMS_unknownFirestation() throws Exception {

		mockMvc.perform(get("/phoneAlert").param("firestation", "99")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0]").doesNotExist());
	}

	@Test
	void testListOfPersonsConcerned() throws Exception {

		mockMvc.perform(get("/firestation").param("firestation", "1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.personsCovered[0].firstName").value("Peter"))
				.andExpect(jsonPath("$.personsCovered[1].lastName").value("Walker"))
				.andExpect(jsonPath("$.nbChildren").value(1)).andExpect(jsonPath("$.nbAdults").value(5));
	}

	@Test
	void testListOfPersonsConcerned_forUnknownFireStation() throws Exception {

		mockMvc.perform(get("/firestation").param("firestation", "99")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.personsCovered[0]").doesNotExist()).andExpect(jsonPath("$.nbChildren").value(0))
				.andExpect(jsonPath("$.nbAdults").value(0));
	}

	@Test
	void testListOfChildrenConcerned() throws Exception {

		mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.listOfChildren[0].firstName").value("Tenley"))
				.andExpect(jsonPath("$.listOfChildren[1].lastName").value("Boyd"))
				.andExpect(jsonPath("$.listOfChildren[1].age").value(5))
				.andExpect(jsonPath("$.listOfAdults[1].firstName").value("Jacob"))
				.andExpect(jsonPath("$.listOfAdults[0].lastName").value("Boyd"));
	}

	@Test
	void testListOfChildrenConcerned_atAnUnknownAddress() throws Exception {

		mockMvc.perform(get("/childAlert").param("address", "9999 Culver")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.listOfChildren[0]").doesNotExist());
	}

	@Test
	void testListOfInhabitantAtAnAddress() throws Exception {

		mockMvc.perform(get("/fire").param("address", "1509 Culver St")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.inhabitantsAtAnaddress[0].firstName").value("John"))
				.andExpect(jsonPath("$.inhabitantsAtAnaddress[1].lastName").value("Boyd"))
				.andExpect(jsonPath("$.address").value("1509 Culver St"))
				.andExpect(jsonPath("$.fireStationNumber").value(3));

	}

	@Test
	void testListOfInhabitantAtAnAddress_withUnkonwnAddress() throws Exception {

		mockMvc.perform(get("/fire").param("address", "addressKO")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.inhabitantsAtAnaddress[0]").doesNotExist());
				//.andExpect(jsonPath("$.address").value(null)).andExpect(jsonPath("$.fireStationNumber").value(0));
	}

	@Test
	void testListOfInhabitantConcernedByAStation() throws Exception {
		mockMvc.perform(get("/flood/stations").param("stations", "2")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].fireStationNumber").value(2))
				.andExpect(jsonPath("$[0].address").value("29 15th St"))
				.andExpect(jsonPath("$[0].inhabitantsAtAnaddress[0].lastName").value("Marrack"))
				.andExpect(jsonPath("$[0].inhabitantsAtAnaddress[0].firstName").value("Jonanathan"))
				.andExpect(jsonPath("$[1].fireStationNumber").value(2))
				.andExpect(jsonPath("$[1].address").value("892 Downing Ct"))
				.andExpect(jsonPath("$[1].inhabitantsAtAnaddress[1].firstName").value("Warren"))
				.andExpect(jsonPath("$[1].inhabitantsAtAnaddress[0].medications[1]").value("hydrapermazol:900mg"));
	}

	@Test
	void testInformationsForAGivenPersonAndHisFamily() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("lastName", "Stelzer");
		params.add("firstName", "Brian");

		mockMvc.perform(get("/personInfo").params(params)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].age").value(46))
				.andExpect(jsonPath("$[0].address").value("947 E. Rose Dr"))
				.andExpect(jsonPath("$[2].email").value("bstel@email.com"))
				.andExpect(jsonPath("$[2].firstName").value("Kendrik"))
				.andExpect(jsonPath("$[1].email").value("ssanw@email.com")).andExpect(jsonPath("$[1].age").value(42))
				.andExpect(jsonPath("$[2].medications[0]").value("noxidian:100mg"));

	}

	@Test
	void testListOfEmailsInTheCity() throws Exception {

		mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0]").value("jaboyd@email.com"))
				.andExpect(jsonPath("$[1]").value("drk@email.com"));
	}
}
