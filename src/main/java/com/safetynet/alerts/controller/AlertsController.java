package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alerts.dto.InhabitantsCoveredDTO;
import com.safetynet.alerts.service.AlertsService;

@Controller
public class AlertsController {
	
	@Autowired
	AlertsService alertsService;
	

	
	@GetMapping("/phoneAlert")
	public ResponseEntity<List<String>> SMS(@RequestParam(value = "firestation", required = true) int firestation){
		return new ResponseEntity <> (alertsService.getPhoneforPersonsCoveredByStation(firestation), HttpStatus.OK);
		
	}
	
	@GetMapping("/firestation")
	public ResponseEntity<InhabitantsCoveredDTO> listOfPersonsConcerned(@RequestParam(value = "firestation", required = true) int firestation){
		return new ResponseEntity <> (alertsService.getListOfPersonsCoveredByStation(firestation), HttpStatus.OK);
		
	}
	
	@GetMapping("/childAlert")
	public ResponseEntity<Object> listOfChildrenConcerned(@RequestParam(value = "address", required = true) String address){
		return new ResponseEntity <> (alertsService.getListOfChildrenAtAnAdress(address), HttpStatus.OK);
		
	}
}
