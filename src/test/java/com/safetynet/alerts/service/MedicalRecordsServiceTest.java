package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.exceptions.AlreadyExistsException;
import com.safetynet.alerts.exceptions.DataNotFoundException;
import com.safetynet.alerts.model.MedicalRecords;
import com.safetynet.alerts.repository.MedicalRecordsRepository;

@ExtendWith(MockitoExtension.class)
class MedicalRecordsServiceTest {
	private static List<MedicalRecords> medicalRecords = new ArrayList<>();

	private static MedicalRecords MRExist = new MedicalRecords();
	private static MedicalRecords MRNew = new MedicalRecords();
	private static MedicalRecords MRKO = new MedicalRecords();

	@InjectMocks
	private static MedicalRecordsService medicalRecordsService;

	@Mock
	private static MedicalRecordsRepository medicalRecordsRepositoryMock;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		medicalRecords.add(new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"),
				List.of("rhume Des Foins")));
		medicalRecords.add(new MedicalRecords("firstName2", "lastName2", "25/12/20005", List.of("pivalone 60"),
				List.of("rhume Des Foins", "acariens")));
		medicalRecords.add(new MedicalRecords("firstName3", "lastName3", "25/12/1965", List.of(), List.of()));
		medicalRecords.add(new MedicalRecords("firstName4", "lastName4", "25/12/1932",
				List.of("doliprane 1000mg", "cardio 200"), List.of("iode")));
		medicalRecords.add(new MedicalRecords("firstName5", "lastName5", "25/12/1948",
				List.of("doliprane 1000mg", "tramadol 100"), List.of()));

		MRExist = new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"),
				List.of("rhume Des Foins"));
		MRNew = new MedicalRecords("Nouveau", "prenom", "31/12/1931", List.of("dolirhume 100mg"),
				List.of("allergie aux poils de chat"));
		MRKO = new MedicalRecords("", "MRKOCarNomEmpty", "25/12/1951", List.of("antalgy 50mg"), List.of());
	}

	@Test
	void testFindMRByNameAndFirstName_withAnExistingName() throws DataNotFoundException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(anyString(), anyString())).thenReturn(MRExist);
		// when
		MedicalRecords result = medicalRecordsService.findMRByNameAndFirstName("lastName1", "firstName1");
		// Then
		assertThat(result).isEqualTo(MRExist);
		verify(medicalRecordsRepositoryMock, times(1)).findMRByNameAndFirstName("lastName1", "firstName1");

	}

	@Test
	void testFindMRByNameAndFirstName_withAnUnknownName() throws DataNotFoundException {
		// given
		String nameBidon = "Bidon";
		String prenomBidon = "Antoine";
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(nameBidon, prenomBidon)).thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class,
				() -> medicalRecordsService.findMRByNameAndFirstName(nameBidon, prenomBidon));
	}

	@Test
	void testGetMedicalRecords() {
		// given
		when(medicalRecordsRepositoryMock.getMedicalRecords()).thenReturn(medicalRecords);
		// when
		List<MedicalRecords> result = medicalRecordsService.getMedicalRecords();
		// then
		assertThat(result).isEqualTo(medicalRecords);

	}

	@Test
	void testAddMedicalRecords_withANewMR_MustBeOK() throws AlreadyExistsException, DataNotFoundException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(MRNew.getLastName(), MRNew.getFirstName()))
				.thenReturn(null);
		// when
		String result = medicalRecordsService.addMedicalRecords(MRNew);
		// then
		assertThat(result).isEqualTo(
				"MedicalRecords for : " + MRNew.getLastName() + " - " + MRNew.getFirstName() + " has been added");
	}

	@Test
	void testAddMedicalRecords_withAnOldPerson_MustBeKO() throws AlreadyExistsException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(MRExist.getLastName(), MRExist.getFirstName()))
				.thenReturn(MRExist);
		// Then
		assertThrows(AlreadyExistsException.class, () -> medicalRecordsService.addMedicalRecords(MRExist));
	}

	@Test
	void testAddMedicalRecords_WithABadNameOrFirstName() throws AlreadyExistsException, DataNotFoundException {
		// Then
		assertThrows(DataNotFoundException.class, () -> medicalRecordsService.addMedicalRecords(MRKO));
		

	}

	@Tag("deleteMR")
	@Test
	void testDeletemedicalRecords_withAnNewPerson_MustBeKO() throws DataNotFoundException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(MRNew.getLastName(), MRNew.getFirstName()))
				.thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> medicalRecordsService.deleteMedicalRecords(MRNew));
	}

	@Tag("deleteMR")
	@Test
	void testDeletemedicalRecords_withAnOldPerson_MustBeOK() throws DataNotFoundException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(MRExist.getLastName(), MRExist.getFirstName()))
				.thenReturn(MRExist);
		// when
		String result = medicalRecordsService.deleteMedicalRecords(MRExist);
		// then
		assertThat(result).isEqualTo(
				"MedicalRecords for : " + MRExist.getLastName() + " - " + MRExist.getFirstName() + " has been deleted");
	}

	@Tag("updateMR")
	@Test
	void testUpdatemedicalRecords_withAnNewPerson_MustBeKO() throws DataNotFoundException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(MRNew.getLastName(), MRNew.getFirstName()))
				.thenReturn(null);
		// Then
		assertThrows(DataNotFoundException.class, () -> medicalRecordsService.updateMedicalRecords(MRNew));
	}

	@Tag("updateMR")
	@Test
	void testUpdatePerson_withAnOldPerson_MustBeOK() throws DataNotFoundException {
		// given
		when(medicalRecordsRepositoryMock.findMRByNameAndFirstName(MRExist.getLastName(), MRExist.getFirstName()))
				.thenReturn(MRExist);
		// when
		String result = medicalRecordsService.updateMedicalRecords(MRExist);
		// then
		assertThat(result).isEqualTo(
				"MedicalRecords for : " + MRExist.getLastName() + " - " + MRExist.getFirstName() + " has been updated");
	}

}
