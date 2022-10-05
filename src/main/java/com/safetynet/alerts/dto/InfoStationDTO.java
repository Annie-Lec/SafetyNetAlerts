package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

public class InfoStationDTO {
	
	int fireStationNumber;
	String address;
	List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO = new ArrayList<>();
	/**
	 * @param fireStationNumber
	 * @param address
	 * @param inhabitantsAtAnaddressDTO
	 */
	public InfoStationDTO(int fireStationNumber, String address,
			List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO) {
		this.fireStationNumber = fireStationNumber;
		this.address = address;
		this.inhabitantsAtAnaddressDTO = inhabitantsAtAnaddressDTO;
	}
	public int getFireStationNumber() {
		return fireStationNumber;
	}
	public void setFireStationNumber(int fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<InhabitantsAtAnaddressDTO> getInhabitantsAtAnaddressDTO() {
		return inhabitantsAtAnaddressDTO;
	}
	public void setInhabitantsAtAnaddressDTO(List<InhabitantsAtAnaddressDTO> inhabitantsAtAnaddressDTO) {
		this.inhabitantsAtAnaddressDTO = inhabitantsAtAnaddressDTO;
	}
	


}
