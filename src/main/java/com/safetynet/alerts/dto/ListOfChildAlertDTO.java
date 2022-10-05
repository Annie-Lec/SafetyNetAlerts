package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

public class ListOfChildAlertDTO {
	List<NameFirstnameAndAgeDTO> ListOfChildren = new ArrayList<>();

	List<NameAndFirstNameDTO> ListOfAdults = new ArrayList<>();

	/**
	 * Constructor using fields
	 * @param listOfChildren
	 * @param listOfAdults
	 */
	public ListOfChildAlertDTO(List<NameFirstnameAndAgeDTO> listOfChildren, List<NameAndFirstNameDTO> listOfAdults) {
		ListOfChildren = listOfChildren;
		ListOfAdults = listOfAdults;
	}

	public ListOfChildAlertDTO() {}



	public List<NameFirstnameAndAgeDTO> getListOfChildren() {
		return ListOfChildren;
	}

	public void setListOfChildren(List<NameFirstnameAndAgeDTO> listOfChildren) {
		ListOfChildren = listOfChildren;
	}

	public List<NameAndFirstNameDTO> getListOfAdults() {
		return ListOfAdults;
	}

	public void setListOfAdults(List<NameAndFirstNameDTO> listOfAdults) {
		ListOfAdults = listOfAdults;
	}

}
