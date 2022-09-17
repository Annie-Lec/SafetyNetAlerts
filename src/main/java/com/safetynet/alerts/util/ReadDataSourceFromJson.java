package com.safetynet.alerts.util;

import java.io.File;
import java.io.FileNotFoundException;
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
	 * ObjectMapper to mappe file data
	 */
	private ObjectMapper mapper = new ObjectMapper();

	/*
	 * JsonNode which stores all data
	 */
	private JsonNode dataNode = null;

	@Autowired
	private PersonRepository personRepository;


	public ReadDataSourceFromJson() {
	}


	@Override
	public void readData() throws IOException {
		try {
			logger.debug("loading json file ");
			dataNode = mapper.readTree(new File(property.getJsonFilePath()));

			logger.debug("start reading Person object ");
			JsonNode personsNode = dataNode.path("persons");
			Person person = null;
			for (JsonNode personNode : personsNode) {
				try {
					logger.debug("start map json data with Person object ");
					person = mapper.treeToValue(personNode, Person.class);
					personRepository.addPerson(person);
					System.out.println(person);
				} catch (IllegalArgumentException e) {
					logger.error("error while mapping json data to Person object ", e);
				}
			}
			logger.debug("finish reading Person object ");
		} catch (FileNotFoundException e) {
			logger.error("error file json not found ", e);
		}
	}

}
