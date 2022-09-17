package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.IReadDataSource;
import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootApplication
public class SafetynetalertsApplication implements CommandLineRunner {

	@Autowired
	IReadDataSource readDataSource = new ReadDataSourceFromJson();
	
	//PersonRepository personRepository = new PersonRepository();

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		readDataSource.readData();

	}

}
