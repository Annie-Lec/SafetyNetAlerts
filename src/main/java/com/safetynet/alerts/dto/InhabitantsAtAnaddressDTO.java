package com.safetynet.alerts.dto;

import java.util.List;
import java.util.Objects;

public class InhabitantsAtAnaddressDTO {
	
	private String lastName;
	private String firstName;
	private String phone;
	private int age;
	private List<String> medications;
	private List<String> allergies;
	
	/**
	 * @param lastName
	 * @param firstName
	 * @param age
	 * @param medications
	 * @param allergies
	 */
	public InhabitantsAtAnaddressDTO(String lastName, String firstName, String phone, int age, List<String> medications,
			List<String> allergies) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.phone = phone;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, allergies, firstName, lastName, medications, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InhabitantsAtAnaddressDTO other = (InhabitantsAtAnaddressDTO) obj;
		return age == other.age && Objects.equals(allergies, other.allergies)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(medications, other.medications) && Objects.equals(phone, other.phone);
	}
	
	
	
	

}
