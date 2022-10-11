package com.safetynet.alerts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.FireStation;

public class FireStationRepositoryTest {

	private static FireStationRepository fireStationRepository;
	private static List<FireStation> fireStations = new ArrayList<>();

	@BeforeAll
	public static void setUpBeforeClass()  {
		fireStations.add(new FireStation("address1", 1));
		fireStations.add(new FireStation("address2", 1));
		fireStations.add(new FireStation("address3", 2));
		fireStations.add(new FireStation("address4", 1));
		fireStations.add(new FireStation("address5", 2));

		fireStationRepository = new FireStationRepository(fireStations);

	}

	@Test
	public void addFireStationTest() {
		// GIVEN
		FireStation newFireStation = new FireStation("newAddress", 1);
		// WHEN
		fireStationRepository.addFireStation(newFireStation);
		// THEBN
		assertThat(fireStations).contains(newFireStation);

	}

	@Test
	public void deletePersonTest() {
		// given
		FireStation oldFireStation = new FireStation("address1", 1);
		// when
		fireStationRepository.deleteFireStation(oldFireStation);
		// then
		assertThat(fireStations).doesNotContain(oldFireStation);
	}

	@Test
	public void updateFireStationTest()  {
		// Given
		FireStation updatedFireStation = new FireStation("address1", 4);
		// when
		FireStation result = fireStationRepository.updateFireStation(updatedFireStation);
		// Then
		assertThat(result.getStation()).isEqualTo(4);
	}

	@Test
	public void findFireStationsByAddressTest_withAGoodAddress() {
		// Given
		String address = "address1";
		// when
		FireStation result = fireStationRepository.findFireStationsByAddress(address);
		// Then
		assertThat(result).isNotNull();

	}
	
	@Test
	public void findFireStationsByAddressTest_withAUnknownAddress() {
		// Given
		String address = "addressBidon";
		// when
		FireStation result = fireStationRepository.findFireStationsByAddress(address);
		// Then
		assertThat(fireStations).doesNotContain(result);
		assertThat(result).isNull();

	}
	
	
	@Test
	public void findFireStationsByNumberTest_withAGoodNumber() {
		// Given
		int number = 1;
		// when
		List<FireStation> result = fireStationRepository.findFireStationByNumber(number);
		// Then
		assertThat(fireStations).containsAll(result);
		assertThat(result).isNotNull();

	}
	
	@Test
	public void findFireStationsByNumberTest_withAUnknownNumber() {
		// Given
		int number = 99;
		// when
		List<FireStation> result = fireStationRepository.findFireStationByNumber(number);
		// Then
		assertThat(result).isEmpty();

	}



}
