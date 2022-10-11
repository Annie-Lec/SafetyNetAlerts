package com.safetynet.alerts.service;

import java.util.List;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.core.IsNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
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
	 * @param number of the station
	 * @throws DataNotFoundException
	 */
	public List<FireStation> findFireStationByNumber(int fireStation) {
		logger.debug("find a list of addresses for a firestation : findFireStationByNumber");
		List<FireStation> fireStations = null;
		fireStations = fireStationRepository.findFireStationByNumber(fireStation);
		if (fireStations.size() > 0) {
			logger.info("list of addresses for a firestation found");
			return fireStations;
		} else {
			logger.error("firestation number  :{" + fireStation + "} not found");
			return null;
		}
	}

	/**
	 * Retrieve the list of addresses for a fire station
	 * 
	 * @param number of the station
	 */
	public List<String> findAddressByFireStation(int numberOfireStation) {
		logger.debug("find a list of addresses for a firestation : findAddressByFireStation");
		List<FireStation> fireStations = null;
		// because it could be double value in the file... on utilise distinct

		fireStations = fireStationRepository.findFireStationByNumber(numberOfireStation);
		if (fireStations.size() > 0) {
			return fireStations.stream().map(FireStation::getAddress).distinct().collect(Collectors.toList());
		} else {
			logger.error("firestation number  :{" + numberOfireStation + "} not found, couldn't retrieve address");
			return null;
		}

	}

	public int findTheNumberOfFirestationByAddress(String address) throws DataNotFoundException {
		logger.debug("find the number of a firestation for a given address");
		FireStation fs = fireStationRepository.findFireStationsByAddress(address);
		if (fs != null) {
			return fs.getStation();
		} else {
			logger.error("The address  :{" + address + "} not found, couldn't retrieve fireStation");
			throw new DataNotFoundException("The address :" + address + " not found , couldn't retrieve fireStation");
		}

	}

	// CRUD - CRUD
	/**
	 * READ the List Of Fire Stations
	 */
	public List<FireStation> getFireStationsService() {
		logger.debug("get all the firestation ");
		return fireStationRepository.getFireStations();
	}

	/**
	 * CREATE a FireStation
	 * 
	 * @param fireStation
	 * 
	 */
	public String addFireStation(FireStation fireStation) {
		logger.debug("Add a firestation  : number of a Fire Station for a new address");
		String message;

		if (!fireStation.getAddress().isEmpty() && !fireStation.getAddress().isBlank()
				&& fireStation.getStation() > 0) {
			FireStation fireStationToAdd = fireStationRepository.findFireStationsByAddress(fireStation.getAddress());
			if (fireStationToAdd == null) {
				fireStationRepository.addFireStation(fireStation);
				message = "Fire Station : " + fireStation.getAddress() + " - station number : "
						+ fireStation.getStation() + " has been added";
			} else {
				message = "A FireStation already exists at the address " + fireStation.getAddress();
				logger.error(message);
			}
		} else {
			message = "No Fire station Added : missing address or firestation number null !";
		}
		return message;
	}

	/**
	 * 
	 * @param fireStation
	 * @return
	 */
	public String deleteFireStation(FireStation fireStation) {
		logger.debug("Delete a firestation ");
		String message;

		if (!fireStation.getAddress().isEmpty() && !fireStation.getAddress().isBlank()
				&& fireStation.getStation() >= 0) {
			FireStation fireStationToDelete = fireStationRepository.findFireStationsByAddress(fireStation.getAddress());
			if (fireStationToDelete == null) {
				message = "No Fire station Deleted, no such station found in the database";
			} else if (fireStationToDelete.getStation() == fireStation.getStation()) {
				fireStationRepository.deleteFireStation(fireStation);
				message = "Fire Station : " + fireStation.getAddress() + " - station number : "
						+ fireStation.getStation() + " has been deleted";
			} else {
				message = "Be Careful, a FireStation exists at the address " + fireStation.getAddress()
						+ " but NOT with this number :" + fireStation.getStation();
				logger.error(message);
			}
		} else {
			message = "No Fire station Deleted : address not found or firestation number null !";
		}
		return message;
	}

	/**
	 * CREATE a FireStation
	 * 
	 * @param fireStation
	 * 
	 */
	public String updateFireStation(FireStation fireStation) {
		logger.debug("update a firestation  : number of a Fire Station for an address");
		String message;

		if (!fireStation.getAddress().isEmpty() && !fireStation.getAddress().isBlank()
				&& fireStation.getStation() > 0) {
			FireStation fireStationToAdd = fireStationRepository.findFireStationsByAddress(fireStation.getAddress());
			if (fireStationToAdd != null) {
				fireStationRepository.updateFireStation(fireStation);
				message = "Fire Station : " + fireStation.getAddress() + " - station number : "
						+ fireStation.getStation() + " has been updated";
			} else {
				message = "A FireStation doesn't exist at the address " + fireStation.getAddress();
				logger.error(message);
			}
		} else {
			message = "No Fire station updated : missing address ou firestation number null !";
		}
		return message;
	}

}
