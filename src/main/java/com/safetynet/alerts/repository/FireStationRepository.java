package com.safetynet.alerts.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.util.IReadDataSource;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

/**
 * 
 * class that contains the management of fire station data 
 *
 */
@Repository
public class FireStationRepository {
	
	private static final Logger logger = LogManager.getLogger("FireStation Repository");

	private List<FireStation> fireStations = new ArrayList<>();
	
	@Autowired
	IReadDataSource dataSource = new ReadDataSourceFromJson();
	@Autowired
	FireStationMapper fireStationMapper = new FireStationMapper();

	public FireStationRepository() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Before running the Fire Station Repository Constructor this method is running to
	 * initiate data source and to extract List of FireStations
	 * 
	 */
	@PostConstruct
	public void initFireStations() {
		System.out.println("initFire initFire initFire");
		setFireStations
		(fireStationMapper.mapToFireStationClass((Any) dataSource.getReadDataFireStations()));
	}

	

	/**
	 * Constructor with parameters
	 * 
	 * @param fireStations
	 */
	public FireStationRepository(List<FireStation> fireStations) {
			this.fireStations = fireStations;
	}

	/**
	 * Save a fireStation in the list of fireStations
	 * 
	 * @param fireStation
	 */
	public void addFireStation(FireStation fireStation) {
		logger.info("saving a fireStation in the data listof fireStation", fireStation);
		this.fireStations.add(fireStation);
	}
	
	/**
	 * Delete a fireStation in the list of fireStations
	 * 
	 * @param fireStation
	 */
	public void deleteFireStation(FireStation fireStation) {
		logger.info("deleting a fireStation in the data listof fireStation", fireStation);
		this.fireStations.remove(fireStation);
	}
	
	/**
	 * Update a fireStation in the list of fireStations
	 * 
	 * @param fireStation
	 * @throws IOException 
	 */
	public FireStation updateFireStation(FireStation fireStation) throws IOException {
		FireStation fireStationToUpdate = findFireStationsByAddress(fireStation.getAddress());
		logger.info("update  a fireStation in the data listof fireStation", fireStation);
		deleteFireStation(fireStationToUpdate);
		addFireStation(fireStation);
		return fireStation;
	}
	
	
	
	/**
	 * Retrieve the list of FireStations at the address
	 * 
	 * @param address
	 */
	public FireStation findFireStationsByAddress(String address) throws IOException {
		logger.debug("find a list of fireStations at an address : findFireStationsByAddress");
		//System.out.println("find a list of fireStations at an address");
		return fireStations.stream().filter(p -> p.getAddress().equalsIgnoreCase(address)).findFirst().get();
	}

	/**
	 * Retrieve the list of FireStations for a station number
	 * 
	 * @param number in String of the station
	 */
	public List<FireStation> findFireStationByNumber(int fireStation) throws IOException {
		logger.debug("find a list of addresses for a firestation : findFireStationByNumber");
	//return fireStations.stream().filter(p -> p.getStation().equalsIgnoreCase(fireStation)).collect(Collectors.toList());
		return fireStations.stream().filter(p -> p.getStation() == fireStation ).collect(Collectors.toList());
	}


	public List<FireStation> getFireStations() {
		return fireStations;
	}

	public void setFireStations(List<FireStation> fireStations) {
		this.fireStations = fireStations;
	}
	
	


}
