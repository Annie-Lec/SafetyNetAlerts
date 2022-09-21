package com.safetynet.alerts.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jsoniter.any.Any;
import com.safetynet.alerts.model.Person;

@Component
public class PersonMapper {
	public static final Logger logger = LogManager.getLogger("PersonMapper Class");
	
	private List<Person> persons = new ArrayList<>();
	
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public PersonMapper() {}

	
	public List<Person> mapToPersonClass(Any personsReader) {
		for (Any p : personsReader) {
			persons.add(p.as(Person.class));
		}
		return persons;
	}
	

}
