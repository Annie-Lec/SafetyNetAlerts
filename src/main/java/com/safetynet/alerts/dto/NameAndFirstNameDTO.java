package com.safetynet.alerts.dto;

public class NameAndFirstNameDTO {
	
	/**
	 * person's first name
	 */
	private String firstName;
	/**
	 * person's last name
	 */
	private String lastName;
	/**
	 * @param firstName
	 * @param lastName
	 */
	public NameAndFirstNameDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
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

}
