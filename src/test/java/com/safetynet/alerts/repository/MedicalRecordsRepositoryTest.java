package com.safetynet.alerts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.MedicalRecords;

public class MedicalRecordsRepositoryTest {
	
	private static MedicalRecordsRepository medicalRecordsRepository;
	private static List<MedicalRecords> medicalRecords = new ArrayList<>();

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		
		medicalRecords.add(new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"), List.of("rhume Des Foins")));
		medicalRecords.add(new MedicalRecords("firstName2", "lastName2", "25/12/20005", List.of("pivalone 60"), List.of("rhume Des Foins", "acariens")));
		medicalRecords.add(new MedicalRecords("firstName3", "lastName3", "25/12/1965", List.of(), List.of()));
		medicalRecords.add(new MedicalRecords("firstName4", "lastName4", "25/12/1932", List.of("doliprane 1000mg" , "cardio 200"), List.of("iode")));
		medicalRecords.add(new MedicalRecords("firstName5", "lastName5", "25/12/1948", List.of("doliprane 1000mg" , "tramadol 100"), List.of()));

		medicalRecordsRepository = new MedicalRecordsRepository(medicalRecords);
	}

	@Test
	public void deleteMedicalRecordtest() {
		//GIVEN
		MedicalRecords oldMR = new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 500mg"), List.of("rhume Des Foins"));
		//WHEN
		medicalRecordsRepository.deleteMedicalRecords(oldMR);
		//THEN
		assertThat(medicalRecords).doesNotContain(oldMR);
	}
	
	@Test
	public void addMedicalRecordtest() {
		//GIVEN
		MedicalRecords newMR = new MedicalRecords("Annie", "Lechevalier", "25/09/1971", List.of("doliprane 1000mg"), List.of());
		//WHEN
		medicalRecordsRepository.addMedicalRecords(newMR);
		//THEN
		assertThat(medicalRecords).contains(newMR);
	}
	
	@Test
	public void updateMedicalRecordtest() {
		//GIVEN
		MedicalRecords updatedMR = new MedicalRecords("firstName1", "lastName1", "25/12/1971", List.of("doliprane 1000mg"), List.of("rhume Des Foins", "amidon"));
		//WHEN
		MedicalRecords result = medicalRecordsRepository.updateMedicalRecords(updatedMR);
		//THEN
		assertThat(result).isEqualTo(updatedMR);
		assertThat(result.getAllergies()).isEqualTo(List.of("rhume Des Foins", "amidon"));
		assertThat(result.getMedications()).isEqualTo(List.of("doliprane 1000mg"));
	}

}
