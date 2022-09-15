package com.safetynet.alerts.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * Class of private properties management in the file applicatin.properties,
 * look at the customized properties paragraph
 *
 */
@Configuration
@ConfigurationProperties(prefix = "com.openclassrooms.apisafetynet.configuration")
public class CustomProperties {

	private String jsonFilePath;

	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public void setJsonFilePath(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}

}
