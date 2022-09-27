package com.safetynet.alerts.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jsoniter.any.Any;
import com.safetynet.alerts.model.FireStation;

/**
 * 
 * class that contains the mapping of FireStation data : 
 * JSON to FireStation Class
 * and FireStation Class to JSON
 * 
 *
 */
@Component
public class FireStationMapper {

	public static final Logger logger = LogManager.getLogger("FireStationMapper Class");

	private List<FireStation> fireStations = new ArrayList<>();

	public List<FireStation> getFireStations() {
		return fireStations;
	}

	public void setFireStations(List<FireStation> fireStations) {
		this.fireStations = fireStations;
	}

	public FireStationMapper() {
	}

	public List<FireStation> mapToFireStationClass(Any fireStationsReader) {
		for (Any fs : fireStationsReader) {
	//		fireStations.add(fs.as(FireStation.class));
			fireStations.add(new FireStation(fs.get("address").toString(), fs.get("station").toInt()));
		}
		return fireStations;
	}

}
