package com.safetynet.alerts.dto;

import java.util.Objects;

/**
 * Used to respons a part of EndPoint : firestation?stationNumber=<station_number>
 * @See InhabitantsCoveredDTO
 * @author aNewL
 *
 */
public class PersonsCoveredDTO {
	
	/**
	 * person's first name
	 */
	private String firstName;
	/**
	 * person's last name
	 */
	private String lastName;
	/**
	 * person's address
	 */
	private String address;
	/**
	 * person's phone number
	 */
	private String phone;
	
	
	
	
	
	/**
	 * Constructor using fields
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param phone
	 */
	public PersonsCoveredDTO(String firstName, String lastName, String address, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	
	/**
	 * Constructor without fields
	 */
	public PersonsCoveredDTO() {}
	
	@Override
	public String toString() {
		return "PersonsCoveredDTO [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", phone=" + phone + "]";
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, lastName, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonsCoveredDTO other = (PersonsCoveredDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phone, other.phone);
	}
	

}
