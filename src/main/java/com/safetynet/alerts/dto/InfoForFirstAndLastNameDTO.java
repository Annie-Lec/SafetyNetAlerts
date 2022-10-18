package com.safetynet.alerts.dto;

import java.util.List;
import java.util.Objects;

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
	@Override
	public int hashCode() {
		return Objects.hash(address, age, allergies, email, firstName, lastName, medications);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoForFirstAndLastNameDTO other = (InfoForFirstAndLastNameDTO) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(allergies, other.allergies)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(medications, other.medications);
	}

}
