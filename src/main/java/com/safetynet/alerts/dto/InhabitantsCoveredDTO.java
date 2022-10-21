package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * used by endpoint 1 : firestation?stationNumber=<station_number>
 * @author aNewL
 *
 */
public class InhabitantsCoveredDTO {
	
	/**
	 * personsCovered contains informations [name, adddress, tel] about the people followed by a station
	 */
	private List<PersonsCoveredDTO> personsCovered = new ArrayList<>();
	/**
	 * number of adults
	 */
	private int nbAdults;
	/**
	 * number of children
	 */
	private int nbChildren;
	
	/**
	 * Constructor using fields
	 * @param personsCovered
	 * @param nbAdults
	 * @param nbChildren
	 */
	public InhabitantsCoveredDTO(List<PersonsCoveredDTO> personsCovered, int nbAdults, int nbChildren) {
		super();
		this.personsCovered = personsCovered;
		this.nbAdults = nbAdults;
		this.nbChildren = nbChildren;
	}
	
	/**
	 * Constructor without fields
	 */
	public InhabitantsCoveredDTO() {}
	
	
	public List<PersonsCoveredDTO> getPersonsCovered() {
		return personsCovered;
	}
	public void setPersonsCoveredDTO(List<PersonsCoveredDTO> personsCovered) {
		this.personsCovered = personsCovered;
	}
	public int getNbAdults() {
		return nbAdults;
	}
	public void setNbAdults(int nbAdults) {
		this.nbAdults = nbAdults;
	}
	public int getNbChildren() {
		return nbChildren;
	}
	public void setNbChildren(int nbChildren) {
		this.nbChildren = nbChildren;
	}
	
	
	

}
