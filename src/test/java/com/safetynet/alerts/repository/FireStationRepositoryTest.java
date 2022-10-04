package com.safetynet.alerts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.FireStation;

class FireStationRepositoryTest {

	private static FireStationRepository fireStationRepository;
	private static List<FireStation> fireStations = new ArrayList<>();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		fireStations.add(new FireStation("address1", 1));
		fireStations.add(new FireStation("address2", 1));
		fireStations.add(new FireStation("address3", 2));
		fireStations.add(new FireStation("address4", 1));
		fireStations.add(new FireStation("address5", 2));

		fireStationRepository = new FireStationRepository(fireStations);

	}

	@Test
	void addFireStationTest() {
		// GIVEN
		FireStation newFireStation = new FireStation("newAddress", 1);
		// WHEN
		fireStationRepository.addFireStation(newFireStation);
		// THEBN
		assertThat(fireStations).contains(newFireStation);

	}

}
