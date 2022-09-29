package com.safetynet.alerts.dto;


/**
 * used by ListOfChildAlertDTO ; endpoint childAlert?address=<address>
 * @author aNewL
 * @See ListOfChildAlertDTO
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
	 * age for the minor only
	 */
	private int ageForMinor;
	
	/**
	 * Constructor with fields
	 * @param firstName
	 * @param lastName
	 * @param ageForMinor
	 */
	public NameFirstnameAndAgeDTO(String firstName, String lastName, int ageForMinor) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ageForMinor = ageForMinor;
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
	public int getAgeForMinor() {
		return ageForMinor;
	}
	public void setAgeForMinor(int ageForMinor) {
		this.ageForMinor = ageForMinor;
	}

	
}
