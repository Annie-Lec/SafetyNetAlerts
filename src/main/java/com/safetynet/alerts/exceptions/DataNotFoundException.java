package com.safetynet.alerts.exceptions;

/**
 * Used in the search methods : when the data is not found, not present in 'database'
 * @author aNewL
 *
 */
public class DataNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9118521524159312819L;

	public DataNotFoundException(String message) {
		super(message);
	}



}
