package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Used by endpoint childAlert?address=<address>
 * @author aNewL
 *
 */
public class ListOfChildAlertDTO {
	
	List<NameFirstnameAndAgeDTO> ListOfChildren = new ArrayList<>();
	
	List<String> ListOfAdults = new ArrayList<>();

	/**
	 * Constructor using fields
	 * @param listOfChildren
	 * @param listOfAdults
	 */
	public ListOfChildAlertDTO(List<NameFirstnameAndAgeDTO> listOfChildren, List<String> listOfAdults) {
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

	public List<String> getListOfAdults() {
		return ListOfAdults;
	}

	public void setListOfAdults(List<String> listOfAdults) {
		ListOfAdults = listOfAdults;
	}
	
	

}
