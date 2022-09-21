package com.safetynet.alerts.model;

/**
 * 
 * Person class, is used to contain person's informations like first name, last
 * name and so on
 *
 */
public class Person {

	/**
	 * person's first name
	 */
	private String firstName;

	/**
	 * Empty Constructor
	 */
	public Person() {
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param city
	 * @param zip
	 * @param phone
	 * @param email
	 */
	public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * person's last name
	 */
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	/**
	 * person's address
	 */
	private String address;

	/**
	 * the person's city
	 */
	private String city;

	/**
	 * the person's zip code of the address
	 */
	private String zip;

	/**
	 * the person's phone
	 */
	private String phone;

	/**
	 * the person's email
	 */
	private String email;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", city=" + city
				+ ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
	}

}
