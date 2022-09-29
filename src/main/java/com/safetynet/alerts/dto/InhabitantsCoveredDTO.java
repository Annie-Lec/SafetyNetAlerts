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
	 * personsCoveredDTO contains informations [name, adddress, tel] about the people followed by a station
	 */
	private List<PersonsCoveredDTO> personsCoveredDTO = new ArrayList<>();
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
	 * @param personsCoveredDTO
	 * @param nbAdults
	 * @param nbChildren
	 */
	public InhabitantsCoveredDTO(List<PersonsCoveredDTO> personsCoveredDTO, int nbAdults, int nbChildren) {
		super();
		this.personsCoveredDTO = personsCoveredDTO;
		this.nbAdults = nbAdults;
		this.nbChildren = nbChildren;
	}
	
	/**
	 * Constructor without fields
	 */
	public InhabitantsCoveredDTO() {}
	
	
	public List<PersonsCoveredDTO> getPersonsCoveredDTO() {
		return personsCoveredDTO;
	}
	public void setPersonsCoveredDTO(List<PersonsCoveredDTO> personsCoveredDTO) {
		this.personsCoveredDTO = personsCoveredDTO;
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
