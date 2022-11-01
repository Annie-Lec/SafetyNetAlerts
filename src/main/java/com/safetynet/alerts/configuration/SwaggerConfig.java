package com.safetynet.alerts.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		  info = @Info(
				  title = "SafetyNet Alerts",
				  description = "" +
				    "API pour les services d urgence",
				  contact = @Contact(
				    name = "AnnieLec", 
				    email = "doudou.azerty@gmail.com"
				  )),
				  servers = @Server(url = "http://localhost:8080")
				)
@Configuration
@EnableWebMvc
public class SwaggerConfig {
	
	@Bean
	public GroupedOpenApi api() {
		GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
		return groupedOpenApi;
	}

}
