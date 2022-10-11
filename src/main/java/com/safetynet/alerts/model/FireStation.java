package com.safetynet.alerts.model;

import java.util.Objects;

/**
 * 
 * FireStation class is used to contain fire station data : The number of the
 * fire station and the addresses covered by the station
 * 
 */
public class FireStation {

	/**
	 * address covered by fire station
	 */
	private String address;

	/**
	 * fire station number
	 */
	private int station;

	/**
	 * Empty Constructor
	 */
	public FireStation() {
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param address
	 * @param station
	 */
	public FireStation(String address, int station) {
		super();
		this.address = address;
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "FireStation [address=" + address + ", station=" + station + "]";
	}

	/**
	 * checks if an object is equal to this Firestation. Mandatory when we want to
	 * compare 2 instances of FireStation : to delete an instance of FireStation in
	 * the List Of FireStations for example
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FireStation other = (FireStation) obj;
		return Objects.equals(address, other.address) && station == other.station;
	}

}
