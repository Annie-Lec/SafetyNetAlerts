package com.safetynet.alerts;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class SafetynetalertsApplication {
	
	private static final Logger logger = LogManager.getLogger("SafetynetalertsApplication");
//	private static final Logger logger = LoggerFactory.getLogger(SafetynetalertsApplication.class);


	public static void main(String[] args) {
		logger.debug("AlertsApplication start");
		SpringApplication.run(SafetynetalertsApplication.class, args);
	}

}
