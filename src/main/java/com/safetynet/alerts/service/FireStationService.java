package com.safetynet.alerts.service;

import java.io.IOException;

import java.util.List;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;

@Service
public class FireStationService {

	private static final Logger logger = LogManager.getLogger("FireStationService");

	@Autowired
	FireStationRepository fireStationRepository;

	/**
	 * Retrieve the list of FireStations for a number of station
	 * 
	 * @param number in String of the station
	 */
	public List<FireStation> findFireStationByNumber(int fireStation) throws IOException {
		logger.debug("find a list of addresses for a firestation");
		List<FireStation> fireStations = null;
		fireStations = fireStationRepository.findFireStationByNumber(fireStation);
		return fireStations;
	}

	/**
	 * Retrieve the list of addresses for a fire station
	 * 
	 * @param number of the station
	 */
	public List<String> findAddressByFireStation(int numberOfireStation) throws IOException {
		logger.debug("find a list of addresses for a firestation findAddressByFireStation");
		//System.out.println("find a list of addresses for a firestation findAddressByFireStation");
		List<FireStation> fireStations = null;
		// because it could be double value in the file...

		fireStations = fireStationRepository.findFireStationByNumber(numberOfireStation);
		//System.out.println(fireStations.stream().map(FireStation::getAddress).collect(Collectors.toList()));
		return fireStations.stream().map(FireStation::getAddress).distinct().collect(Collectors.toList());

	}
	
	
	public int findTheNumberOfFirestationByAddress(String address) throws IOException {
		logger.debug("find the number of a firestation for a given address");
		FireStation fs = fireStationRepository.findFireStationsByAddress(address);
		return fs.getStation();
	}

}
