package com.safetynet.alerts.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
	
	@Bean
	public GroupedOpenApi api() {
		GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
		return groupedOpenApi;
	}

}
