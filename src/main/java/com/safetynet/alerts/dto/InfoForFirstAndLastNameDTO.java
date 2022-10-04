package com.safetynet.alerts.dto;

import java.util.List;

public class InfoForFirstAndLastNameDTO {
	
	private String lastName;
	private String firstName;
	private String address;
	private String email;
	private int age;
	private List<String> medications;
	private List<String> allergies;
	/**
	 * @param lastName
	 * @param firstName
	 * @param address
	 * @param email
	 * @param age
	 * @param medications
	 * @param allergies
	 */
	public InfoForFirstAndLastNameDTO(String lastName, String firstName, String address, String email, int age,
			List<String> medications, List<String> allergies) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.email = email;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
