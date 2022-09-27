package com.safetynet.alerts.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class AgeCalculator {
	
	/**
	 * Create a SLF4J/LOG4J LOGGER instance.
	 */
	public static final Logger logger = LogManager.getLogger("AgeCalculator");
			
	/**
	 * calculate calculate age for a given birth date
	 * 
	 * @param birthdate
	 * @return age
	 */
	public static int calculate(String birthdate) {
		logger.debug(" calculating the age for birthdate {}", birthdate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate date = LocalDate.parse(birthdate, formatter);
		LocalDate now = LocalDate.now();
		Period period = Period.between(now, date);
		return Math.abs(period.getYears());

	}

}
