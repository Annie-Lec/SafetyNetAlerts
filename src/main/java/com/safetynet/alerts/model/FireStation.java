package com.safetynet.alerts.model;

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
	
	

}
