
package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

	private static List<FireStation> fireStations = new ArrayList<>();
	private static List<FireStation> fireStations1 = new ArrayList<>();

	private static FireStation fireStationUnitaire = new FireStation();
	private static FireStation fireStationNew = new FireStation();
	private static FireStation fireStationKO = new FireStation();

	@InjectMocks
	private static FireStationService fireStationService;

	@Mock
	private static FireStationRepository fireStationRepositoryMock;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		fireStations.add(new FireStation("address1", 1));
		fireStations.add(new FireStation("address2", 1));
		fireStations.add(new FireStation("address3", 2));
		fireStations.add(new FireStation("address4", 1));
		fireStations.add(new FireStation("address5", 2));

		fireStations1.add(new FireStation("address1", 1));
		fireStations1.add(new FireStation("address2", 1));
		fireStations1.add(new FireStation("address4", 1));

		fireStationUnitaire = new FireStation("addresse pour le FS Unitaire", 10);
		fireStationNew = new FireStation("8 rue Neuve", 10);
		fireStationKO = new FireStation("8 rue Bof", 0);

	}

	@Tag("FindFireStationByNumber")
	@Test
	void testFindFireStationByNumber_withAgoodNumber() {
		// Given
		when(fireStationRepositoryMock.findFireStationByNumber(1)).thenReturn(fireStations1);
		// when
		List<FireStation> result = fireStationService.findFireStationByNumber(1);
		// Then
		assertThat(result).containsAll(fireStations1);
		verify(fireStationRepositoryMock, times(1)).findFireStationByNumber(1);
	}

	@Tag("FindFireStationByNumber")
	@Test
	void testFindFireStationByNumber_withRepositorySendingEmptyList() {

		int numberOfStationUnknown = 10;
		// Given
		when(fireStationRepositoryMock.findFireStationByNumber(any(Integer.class))).thenReturn(new ArrayList<>());
		// when
		List<FireStation> result = fireStationService.findFireStationByNumber(numberOfStationUnknown);
		// Then
		// assertThat(result).containsAll(fireStations1);
		verify(fireStationRepositoryMock, times(1)).findFireStationByNumber(numberOfStationUnknown);
		assertNull(result);
	}

	@Tag("FindAddressByFireStation")
	@Test
	void testFindAddressByFireStation_withAGoodNumber() {
		// Given
		when(fireStationRepositoryMock.findFireStationByNumber(1)).thenReturn(fireStations1);
		// when
		List<String> result = fireStationService.findAddressByFireStation(1);
		// Then
		assertThat(result).contains("address1");
		verify(fireStationRepositoryMock, times(1)).findFireStationByNumber(1);
	}

	@Tag("FindAddressByFireStation")
	@Test
	void testFindAddressByFireStation_withABadNumber() {

		// Given
		int numberOfStationUnknown = 10;
		when(fireStationRepositoryMock.findFireStationByNumber(any(Integer.class))).thenReturn(new ArrayList<>());
		// when
		List<String> result = fireStationService.findAddressByFireStation(numberOfStationUnknown);
		// Then
		assertNull(result);
		verify(fireStationRepositoryMock, times(1)).findFireStationByNumber(numberOfStationUnknown);

	}

	@Tag("findTheNumberOfFirestationByAddress")
	@Test
	void testFindTheNumberOfFirestationByAddress_withAnExistingAddress() throws DataNotFoundException {

		// Given
		when(fireStationRepositoryMock.findFireStationsByAddress(anyString())).thenReturn(fireStationUnitaire);
		// When
		int result = fireStationService.findTheNumberOfFirestationByAddress(fireStationUnitaire.getAddress());
		// Then
		assertThat(result).isEqualTo(10);
		verify(fireStationRepositoryMock, times(1)).findFireStationsByAddress(anyString());

	}

	@Tag("findTheNumberOfFirestationByAddress")
	@Test
	void testFindTheNumberOfFirestationByAddress_withAnUnknownAddress() throws DataNotFoundException {

		String adressBidon = "8 rue Bidon";

		// Given
		when(fireStationRepositoryMock.findFireStationsByAddress(adressBidon)).thenReturn(null);
		// When

		// Then
		assertThrows(DataNotFoundException.class,
				() -> fireStationService.findTheNumberOfFirestationByAddress(adressBidon));

	}

	@Tag("AddFirestation")
	@Test
	void testAddFireStation_withANewFS_MustBeOK() {

		// given
		when(fireStationRepositoryMock.findFireStationsByAddress(fireStationNew.getAddress())).thenReturn(null);
		// when
		String result = fireStationService.addFireStation(fireStationNew);
		// then
		assertThat(result).isEqualTo("Fire Station : " + fireStationNew.getAddress() + " - station number : "
				+ fireStationNew.getStation() + " has been added");
	}

	@Tag("AddFirestation")
	@Test
	void testAddFireStation_withAnOldFS_MustInformAddressAlreadyExists() {
		// given
		when(fireStationRepositoryMock.findFireStationsByAddress(fireStationUnitaire.getAddress())).thenReturn(fireStationUnitaire);
		// when
		String result = fireStationService.addFireStation(fireStationUnitaire);
		// then
		assertThat(result).isEqualTo("A FireStation already exists at the address "+ fireStationUnitaire.getAddress());

	}

	@Tag("AddFirestation")
	@Test
	void testAddFireStation_withANumber0_MustBeKO_NoFS_Added() {

		// given
		//fireStationKO
		// when
		String result = fireStationService.addFireStation(fireStationKO);
		// then
		assertThat(result).isEqualTo("No Fire station Added : missing address or firestation number null !");

	}

	@Tag("DeleteFirestation")
	@Test
	void testDeleteFireStation_withANewFS_MustBeKO() {

		// given
		when(fireStationRepositoryMock.findFireStationsByAddress(fireStationNew.getAddress())).thenReturn(null);
		// when
		String result = fireStationService.deleteFireStation(fireStationNew);
		// then
		assertThat(result).isEqualTo("No Fire station Deleted, no such station found in the database");
	
	}

	@Tag("DeleteFirestation")
	@Test
	void testDeleteFireStation_withAnOldFS_MustBeOK() {

	}

	@Tag("DeleteFirestation")
	@Test
	void testDeleteFireStation_withANumver0_MustBeKO() {

	}

	@Tag("UpdateFirestation")
	@Test
	void testUpdateFireStation_withANewFS_MustBeKO() {

	}

	@Tag("UpdateFirestation")
	@Test
	void testUpdateFireStation_withAnOldFS_MustBeOK() {

	}

	@Tag("UpdateFirestation")
	@Test
	void testUpdateFireStation_withanUnknownAddress_MustBeKO() {

	}

}
