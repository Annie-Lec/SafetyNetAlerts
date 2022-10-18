package com.safetynet.alerts.service;

import java.util.List;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	 * @return a list of firestation if the param is found in database Else return null
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
	 * @return a list of address if the param is found in database Else return null
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

	/**
	 * 
	 * @param address
	 * @return
	 * @throws DataNotFoundException
	 */
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
	 * @return a message about the creation of a fire station
	 * @param fireStation
	 * 
	 */
	public String addFireStation(FireStation fireStation) throws AlreadyExistsException {
		logger.debug("Add a firestation  : number of a Fire Station with a new address");
		String message;

		if (!fireStation.getAddress().isEmpty() && !fireStation.getAddress().isBlank()
				&& fireStation.getStation() > 0) {
			//si adresse et numero de station sont corrects
			FireStation fireStationToAdd = fireStationRepository.findFireStationsByAddress(fireStation.getAddress());
			if (fireStationToAdd == null) {
				fireStationRepository.addFireStation(fireStation);
				message = "Fire Station : " + fireStation.getAddress() + " - station number : "
						+ fireStation.getStation() + " has been added";
			} else {
				//le fire station existe deja
				message = "A FireStation already exists at the address " + fireStation.getAddress();
				logger.error(message);
				throw new AlreadyExistsException(message);
			}
		} else {
			//si adresse vide ou numero =0
			message = "No Fire station Added : missing address or firestation number null !";
		}
		return message;
	}

	/**
	 * 
	 * @param fireStation
	 * @return a message about the delete of a fire station
	 */
	public String deleteFireStation(FireStation fireStation) throws DataNotFoundException {
		logger.debug("Delete a firestation ");
		String message;

		if (!fireStation.getAddress().isEmpty() && !fireStation.getAddress().isBlank()
				&& fireStation.getStation() > 0) {
			//si adresse et numero de station sont corrects
			FireStation fireStationToDelete = fireStationRepository.findFireStationsByAddress(fireStation.getAddress());
			//le FS n'est pas trouvé en base
			if (fireStationToDelete == null) {
				message = "No Fire station Deleted, no such station found in the database";
				throw new DataNotFoundException(message);
				//le FS existe en base alors on peut le supprimer
			} else if (fireStationToDelete.getStation() == fireStation.getStation()) {
				fireStationRepository.deleteFireStation(fireStation);
				message = "Fire Station : " + fireStation.getAddress() + " - station number : "
						+ fireStation.getStation() + " has been deleted";
				//un FS existe à cette adresse mais pas avec ce numero
			} else {
				message = "Be Careful, a FireStation exists at the address " + fireStation.getAddress()
						+ " but NOT with this number :" + fireStation.getStation();
				logger.error(message);
			}
			//si adresse vide ou numero =0
		} else {
			message = "No Fire station Deleted : address not found or firestation number null !";
			throw new DataNotFoundException(message);
		}
		return message;
	}

	/**
	 * Update a FireStation
	 * @return a message about the update of a fire station
	 * @param fireStation
	 * 
	 */
	public String updateFireStation(FireStation fireStation) throws DataNotFoundException {
		logger.debug("update a firestation  : number of a Fire Station with an address");
		String message;

		if (!fireStation.getAddress().isEmpty() && !fireStation.getAddress().isBlank()
				&& fireStation.getStation() > 0) {
			//si adresse et numero de station sont corrects
			FireStation fireStationToUpdate = fireStationRepository.findFireStationsByAddress(fireStation.getAddress());
			if (fireStationToUpdate != null) {
				fireStationRepository.updateFireStation(fireStation);
				message = "Fire Station : " + fireStation.getAddress() + " - station number : "
						+ fireStation.getStation() + " has been updated";
			} else {
				message = "A FireStation doesn't exist at the address " + fireStation.getAddress();
				logger.error(message);
				throw new DataNotFoundException(message);
			}
		} else {
			message = "No Fire station updated : missing address ou firestation number null !";
			throw new DataNotFoundException(message);
		}
		return message;
	}

}
