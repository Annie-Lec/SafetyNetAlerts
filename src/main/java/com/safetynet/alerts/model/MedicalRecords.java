package com.safetynet.alerts.model;

import java.util.List;

public class MedicalRecords {
	
	/**
	 * person's first name
	 */
	private String firstName;

	/**
	 * person's last name
	 */
	private String lastName;

	/*
	 * person's birth date
	 */
	private String birthdate;

	/**
	 * person's medications
	 */
	private List<String> medications;

	/**
	 * person's allergies
	 */
	private List<String> allergies;

	/**
	 * empty class constructor
	 */
	public MedicalRecords() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param medications
	 * @param allergies
	 */
	public MedicalRecords(String firstName, String lastName, String birthdate, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	

}
