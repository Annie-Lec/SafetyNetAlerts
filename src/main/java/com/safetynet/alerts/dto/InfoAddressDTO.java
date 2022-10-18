package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoAddressDTO {
	
	List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress = new ArrayList<>();
	String address;
	int fireStationNumber;
	/**
	 * @param inhabitantsAtAnaddress
	 * @param address
	 * @param fireStationNumber
	 */
	public InfoAddressDTO(List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress, String address,
			int fireStationNumber) {
		this.inhabitantsAtAnaddress = inhabitantsAtAnaddress;
		this.address = address;
		this.fireStationNumber = fireStationNumber;
	}
	public List<InhabitantsAtAnaddressDTO> getInhabitantsAtAnaddress() {
		return inhabitantsAtAnaddress;
	}
	public void setInhabitantsAtAnaddress(List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddress) {
		this.inhabitantsAtAnaddress = inhabitantsAtAnaddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getFireStationNumber() {
		return fireStationNumber;
	}
	public void setFireStationNumber(int fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, fireStationNumber, inhabitantsAtAnaddress);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoAddressDTO other = (InfoAddressDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(fireStationNumber, other.fireStationNumber)
				&& Objects.equals(inhabitantsAtAnaddress, other.inhabitantsAtAnaddress);
	}

}
