package com.safetynet.alerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.safetynet.alerts.dto.InfoAddressDTO;
import com.safetynet.alerts.dto.InfoForFirstAndLastNameDTO;
import com.safetynet.alerts.dto.InfoStationDTO;
import com.safetynet.alerts.dto.InhabitantsAtAnaddressDTO;
import com.safetynet.alerts.dto.InhabitantsCoveredDTO;
import com.safetynet.alerts.dto.ListOfChildAlertDTO;
import com.safetynet.alerts.dto.NameAndFirstNameDTO;
import com.safetynet.alerts.dto.NameFirstnameAndAgeDTO;
import com.safetynet.alerts.dto.PersonsCoveredDTO;
import com.safetynet.alerts.service.AlertsService;

@WebMvcTest(controllers = AlertsController.class)
class AlertsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AlertsService alertsService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testSMS() throws Exception {
		List<String> phone = new ArrayList<>();
		phone.add("0127446322");
		phone.add("0463241272");

		when(alertsService.getPhoneforPersonsCoveredByStation(3)).thenReturn(phone);

		mockMvc.perform(get("/phoneAlert").param("firestation", "3")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0]").value("0127446322")).andExpect(jsonPath("$[1]").value("0463241272"));
	}

	@Test
	void testListOfPersonsConcerned() throws Exception {

		List<PersonsCoveredDTO> personsCovered = new ArrayList<>();
		personsCovered.add(new PersonsCoveredDTO("prenom1Test", "lastName5", "address3", "0127446322"));
		personsCovered.add(new PersonsCoveredDTO("firstName5", "nom2Test", "address4", "0127446322"));
		int nbChildren = 0;
		int nbAdult = 2;

		InhabitantsCoveredDTO inhabitantsCovered = new InhabitantsCoveredDTO(personsCovered, nbAdult, nbChildren);

		when(alertsService.getListOfPersonsCoveredByStation(3)).thenReturn(inhabitantsCovered);

		mockMvc.perform(get("/firestation").param("firestation", "3")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.personsCovered[0].firstName").value("prenom1Test"))
				.andExpect(jsonPath("$.personsCovered[1].lastName").value("nom2Test"))
				.andExpect(jsonPath("$.nbChildren").value(0)).andExpect(jsonPath("$.nbAdults").value(2));

	}

	@Test
	void testListOfChildrenConcerned() throws Exception {

		List<NameFirstnameAndAgeDTO> listOfChildren = new ArrayList<>();
		listOfChildren.add(new NameFirstnameAndAgeDTO("prenomChild1", "lastName1", 17));
		listOfChildren.add(new NameFirstnameAndAgeDTO("prenomChild2", "nomChild2", 17));
		List<NameAndFirstNameDTO> listOfAdults = new ArrayList<>();
		listOfAdults.add(new NameAndFirstNameDTO("NomAdult1", "firstName1"));
		listOfAdults.add(new NameAndFirstNameDTO("NomAdult2", "prenomAdult2"));

		ListOfChildAlertDTO listOfChildAlertDTO = new ListOfChildAlertDTO(listOfChildren, listOfAdults);

		when(alertsService.getListOfChildrenAtAnAdress("address1")).thenReturn(listOfChildAlertDTO);

		mockMvc.perform(get("/childAlert").param("address", "address1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.listOfChildren[0].firstName").value("prenomChild1"))
				.andExpect(jsonPath("$.listOfChildren[1].lastName").value("nomChild2"))
				.andExpect(jsonPath("$.listOfAdults[1].firstName").value("prenomAdult2"))
				.andExpect(jsonPath("$.listOfAdults[0].lastName").value("NomAdult1"));

	}

	@Test
	void testListOfInhabitantAtAnAddress() throws Exception {
		List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress = new ArrayList<>();
		inhabitantsAtAnaddress.add(new InhabitantsAtAnaddressDTO("Lec", "Ann", "0123456789", 51,
				List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		inhabitantsAtAnaddress.add(new InhabitantsAtAnaddressDTO("Mbou", "Jas", "0123456789", 17,
				List.of("pivalone 60"), List.of("rhume Des Foins", "acariens")));
		String address = "address1";
		int fireStationNumber = 1;

		InfoAddressDTO infoAddress = new InfoAddressDTO(inhabitantsAtAnaddress, address, fireStationNumber);

		when(alertsService.getListOfInhabitantsAtAnAddress("address1")).thenReturn(infoAddress);

		mockMvc.perform(get("/fire").param("address", "address1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.inhabitantsAtAnaddress[0].firstName").value("Ann"))
				.andExpect(jsonPath("$.inhabitantsAtAnaddress[1].lastName").value("Mbou"))
				.andExpect(jsonPath("$.address").value(address)).andExpect(jsonPath("$.fireStationNumber").value(1));

	}

	@Test
	void testListOfInhabitantConcernedByAStation() throws Exception {
		List<InfoStationDTO> infoStation = new ArrayList<>();
		List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress1 = new ArrayList<>();
		inhabitantsAtAnaddress1.add(new InhabitantsAtAnaddressDTO("mouzo", "papy", "0127446322", 73,
				List.of("doliprane 1000mg", "tramadol 100"), List.of()));
		inhabitantsAtAnaddress1.add(new InhabitantsAtAnaddressDTO("mouzo", "mamie", "0127446322", 90,
				List.of("doliprane 1000mg", "cardiol 500"), List.of("iode")));

		List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress2 = new ArrayList<>();
		inhabitantsAtAnaddress2.add(new InhabitantsAtAnaddressDTO("lec", "ann", "0127446322", 51,
				List.of("doliprane 1000mg"), List.of("lescons")));

		infoStation.add(new InfoStationDTO(1, "address1", inhabitantsAtAnaddress1));
		infoStation.add(new InfoStationDTO(1, "address2", inhabitantsAtAnaddress2));

		when(alertsService.getListOfInhabitantsForAStation(List.of(1))).thenReturn(infoStation);

//		String jsonResponse = this.mockMvc.perform(get("/flood/stations").param("stations", "1"))
//				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//		System.out.println(jsonResponse);

		mockMvc.perform(get("/flood/stations").param("stations", "1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].fireStationNumber").value(1))
				.andExpect(jsonPath("$[0].address").value("address1"))
				.andExpect(jsonPath("$[0].inhabitantsAtAnaddress[0].lastName").value("mouzo"))
				.andExpect(jsonPath("$[0].inhabitantsAtAnaddress[1].firstName").value("mamie"))
				.andExpect(jsonPath("$[1].fireStationNumber").value(1))
				.andExpect(jsonPath("$[1].address").value("address2"))
				.andExpect(jsonPath("$[1].inhabitantsAtAnaddress[0].lastName").value("lec"))
				.andExpect(jsonPath("$[0].inhabitantsAtAnaddress[1].medications[0]").value("doliprane 1000mg"));
	}

	@Test
	void testInformationsForAGivenPersonAndHisFamily() throws Exception {
		List<InfoForFirstAndLastNameDTO> infoForFirstAndLastName = new ArrayList<>();
		infoForFirstAndLastName.add(new InfoForFirstAndLastNameDTO("Lec", "Ann", "address1", "email1", 51,
				List.of("doliprane 1000mg"), List.of("Truc")));
		infoForFirstAndLastName.add(new InfoForFirstAndLastNameDTO("Lec", "Jas", "address2", "email2", 17,
				List.of("doliprane 1000mg"), List.of("rhume Des Foins")));
		infoForFirstAndLastName.add(new InfoForFirstAndLastNameDTO("Lec", "Ghy", "address2", "email3", 57,
				List.of("doliprane 500mg"), List.of("asthme")));

		when(alertsService.getInformationsForAGivenPersonAndHisFamily("Ann", "Lec"))
				.thenReturn(infoForFirstAndLastName);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("lastName", "Lec");
		params.add("firstName", "Ann");

//		   String jsonResponse = this.mockMvc.perform(get("/personInfo").param("lastName", "Lec").param("firstName", "Ann")).andExpect(status().isOk())
//					.andReturn().getResponse().getContentAsString();
//		   System.out.println(jsonResponse);

		mockMvc.perform(get("/personInfo").params(params)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$[0].age").value(51))
				.andExpect(jsonPath("$[0].address").value("address1")).andExpect(jsonPath("$[2].email").value("email3"))
				.andExpect(jsonPath("$[2].firstName").value("Ghy")).andExpect(jsonPath("$[1].email").value("email2"))
				.andExpect(jsonPath("$[1].address").value("address2"))
				.andExpect(jsonPath("$[1].allergies[0]").value("rhume Des Foins"));
	}

	@Test
	void testListOfEmailsInTheCity() throws Exception {
		List<String> emails = List.of("email1", "email2", "email3");
		when(alertsService.getEmailforPersonsInTheCity("city1")).thenReturn(emails);

		mockMvc.perform(get("/communityEmail").param("city", "city1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0]").value("email1")).andExpect(jsonPath("$[1]").value("email2"));

	}

}
