package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

public class InfoAddressDTO {
	
	List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO = new ArrayList<>();
	String address;
	String fireStationNumber;
	/**
	 * @param inhabitantsAtAnaddressDTO
	 * @param address
	 * @param fireStationNumber
	 */
	public InfoAddressDTO(List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO, String address,
			String fireStationNumber) {
		this.inhabitantsAtAnaddressDTO = inhabitantsAtAnaddressDTO;
		this.address = address;
		this.fireStationNumber = fireStationNumber;
	}
	public List<InhabitantsAtAnaddressDTO> getInhabitantsAtAnaddressDTO() {
		return inhabitantsAtAnaddressDTO;
	}
	public void setInhabitantsAtAnaddressDTO(List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO) {
		this.inhabitantsAtAnaddressDTO = inhabitantsAtAnaddressDTO;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFireStationNumber() {
		return fireStationNumber;
	}
	public void setFireStationNumber(String fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}

}
