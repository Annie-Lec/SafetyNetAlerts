package com.safetynet.alerts.dto;

import java.util.Objects;

/**
 * used by ListOfChildAlertDTO ; endpoint childAlert?address=<address>
 * 
 * 
 */
public class NameFirstnameAndAgeDTO {
	
	/**
	 * person's first name
	 */
	private String firstName;
	/**
	 * person's last name
	 */
	private String lastName;
	/**
	 * age 
	 */
	private int age;
	
	/**
	 * Constructor with fields
	 * @param firstName
	 * @param lastName
	 * @param age
	 */
	public NameFirstnameAndAgeDTO(String firstName, String lastName, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
		
	/**
	 * Constructor without fields
	 */
	public NameFirstnameAndAgeDTO() {}
	
	
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}


	@Override
	public int hashCode() {
		return Objects.hash(age, firstName, lastName);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NameFirstnameAndAgeDTO other = (NameFirstnameAndAgeDTO) obj;
		return age == other.age && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName);
	}

	
}
