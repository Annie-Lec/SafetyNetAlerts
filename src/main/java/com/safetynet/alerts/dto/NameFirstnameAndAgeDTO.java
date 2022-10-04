package com.safetynet.alerts.dto;


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

	
}
