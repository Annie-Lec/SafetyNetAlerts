package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	
	private static final Logger logger = LogManager.getLogger("PersonRepository");

	private List<FireStation> fireStations = new ArrayList<>();
	
	@Autowired
	IReadDataSource readDataSource = new ReadDataSourceFromJson();

	public FireStationRepository() {
		// TODO Auto-generated constructor stub
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
		System.out.println(fireStation);
	}
	
	public List<FireStation> getFireStations(){
		return fireStations;
	}


}
