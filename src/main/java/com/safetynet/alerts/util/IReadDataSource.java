package com.safetynet.alerts.util;


public interface IReadDataSource {
	
	//public void readData() throws IOException;
	public Object getReadDataPersons();
	public Object getReadDataFireStations();
	public Object getReadDataMedicalRecords();

}
