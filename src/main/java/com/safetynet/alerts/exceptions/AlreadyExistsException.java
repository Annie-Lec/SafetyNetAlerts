package com.safetynet.alerts.exceptions;

/**
 * Used before adding data : we can add record only if it does'nt exist
 * @author aNewL
 *
 */
public class AlreadyExistsException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3386239606888627935L;

	public AlreadyExistsException(String message) {
		super(message);
		
	}

	

}
