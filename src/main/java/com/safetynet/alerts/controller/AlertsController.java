package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alerts.dto.InfoAddressDTO;
import com.safetynet.alerts.dto.InfoForFirstAndLastNameDTO;
import com.safetynet.alerts.dto.InfoStationDTO;
import com.safetynet.alerts.dto.InhabitantsCoveredDTO;
import com.safetynet.alerts.dto.ListOfChildAlertDTO;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.service.AlertsService;

@Controller
public class AlertsController {

	@Autowired
	AlertsService alertsService;

	/**
	 * http://localhost:8080/phoneAlert?firestation=<firestation_number>
	 * 
	 * @param firestation
	 * @return
	 * @throws DataNotFoundException 
	 */
	@GetMapping("/phoneAlert")
	public ResponseEntity<List<String>> SMS(@RequestParam(value = "firestation", required = true) int firestation) throws DataNotFoundException {
		return new ResponseEntity<>(alertsService.getPhoneforPersonsCoveredByStation(firestation), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/firestation?stationNumber=<station_number>
	 * 
	 * @param firestation
	 * @return
	 * @throws DataNotFoundException 
	 */
	@GetMapping("/firestation")
	public ResponseEntity<InhabitantsCoveredDTO> listOfPersonsConcerned(
			@RequestParam(value = "firestation", required = true) int firestation) throws DataNotFoundException {
		return new ResponseEntity<>(alertsService.getListOfPersonsCoveredByStation(firestation), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/childAlert?address=<address>
	 * 
	 * @param address
	 * @return
	 */
	@GetMapping("/childAlert")
	public ResponseEntity<ListOfChildAlertDTO> listOfChildrenConcerned(
			@RequestParam(value = "address", required = true) String address) {
		return new ResponseEntity<>(alertsService.getListOfChildrenAtAnAdress(address), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/fire?address=<address>
	 * 
	 * @param address
	 * @return
	 * @throws DataNotFoundException 
	 */
	@GetMapping("/fire")
	public ResponseEntity<InfoAddressDTO> listOfInhabitantAtAnAddress(
			@RequestParam(value = "address", required = true) String address) throws DataNotFoundException {
		return new ResponseEntity<>(alertsService.getListOfInhabitantsAtAnAddress(address), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	 * 
	 * @param stations
	 * @return
	 * @throws DataNotFoundException 
	 */
	@GetMapping("/flood/stations")
	public ResponseEntity<List<InfoStationDTO>> listOfInhabitantConcernedByAStation(@RequestParam List<Integer> stations) throws DataNotFoundException {
		return new ResponseEntity<>(alertsService.getListOfInhabitantsForAStation(stations), HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	 * 
	 * @param lastName
	 * @param firstName
	 * @return
	 */
	@GetMapping("/personInfo")
	public ResponseEntity<List<InfoForFirstAndLastNameDTO>> informationsForAGivenPersonAndHisFamily(
			@RequestParam String lastName, String firstName) {
		return new ResponseEntity<>(alertsService.getInformationsForAGivenPersonAndHisFamily(firstName, lastName),
				HttpStatus.OK);

	}

	/**
	 * http://localhost:8080/communityEmail?city=<city>
	 * 
	 * @param city
	 * @return
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> listOfEmailsInTheCity(
			@RequestParam(value = "city", required = true) String city) {
		return new ResponseEntity<>(alertsService.getEmailforPersonsInTheCity(city), HttpStatus.OK);

	}

}
