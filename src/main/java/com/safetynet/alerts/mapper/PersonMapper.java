package com.safetynet.alerts.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.jsoniter.any.Any;
import com.safetynet.alerts.model.Person;

/**
 * 
 * class that contains the mapping of Person Records data : 
 * JSON to Persons Class
 * and Persons Class to JSON
 * 
 *
 */
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
	
	public JSONObject personToJSON(Person person) {

		Map<String, Object> mapPerson = new HashMap<>();

		mapPerson.put("firstName", person.getFirstName());
		mapPerson.put("lastName", person.getLastName());
		mapPerson.put("address", person.getAddress());
		mapPerson.put("city", person.getCity());
		mapPerson.put("zip", person.getZip());
		mapPerson.put("phone", person.getPhone());
		mapPerson.put("email", person.getEmail());
		

		JSONObject jsonP = new JSONObject(mapPerson);
		return jsonP ;
	}

	

}
