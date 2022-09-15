package com.safetynet.alerts.util;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.configuration.CustomProperties;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@Service

public class ReadDataSourceFromJson implements IReadDataSource {

private static final Logger logger = LogManager.getLogger("ReadDataSourceFromJson");
	
	@Autowired
	private CustomProperties property;

	/**
	 * a json file path
	 */
	private String jsonFilePath ;

	/**
	 * ObjectMapper to mappe file data
	 */
	private ObjectMapper mapper = new ObjectMapper();

	/*
	 * JsonNode which stores all data
	 */
	private JsonNode dataNode = null;
	
	@Autowired
	private PersonRepository personRepository;

	/**
	 * util.jsonFilePath in the application.properties
	 */
	public ReadDataSourceFromJson(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}
	
	public ReadDataSourceFromJson() {}

	public static Logger getLogger() {
		return logger;
	}

	public CustomProperties getProperty() {
		return property;
	}

	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public JsonNode getDataNode() {
		return dataNode;
	}

	public PersonRepository getPersonRepository() {
		return personRepository;
	}

	@Override
	public void readData() throws IOException {
		dataNode = mapper.readTree(new File(property.getJsonFilePath()));
		
		JsonNode personsNode = dataNode.path("persons");
		Person person = null;
		for (JsonNode personNode : personsNode) {
			try {
				logger.debug("mapping json data to Person object ");
				person = mapper.treeToValue(personNode, Person.class);
				personRepository.addPerson(person);
				System.out.println(person);
			} catch ( IllegalArgumentException e) {
				logger.error("error mapping json data to Person object ", e);
			}

		}
	}


}
