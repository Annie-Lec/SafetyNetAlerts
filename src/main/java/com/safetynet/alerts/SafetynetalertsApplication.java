package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.alerts.util.ReadDataSourceFromJson;

@SpringBootApplication
public class SafetynetalertsApplication implements CommandLineRunner {

	@Autowired
	ReadDataSourceFromJson readDataSource = new ReadDataSourceFromJson();

	public static void main(String[] args) {
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		readDataSource.readData();

	}

}
