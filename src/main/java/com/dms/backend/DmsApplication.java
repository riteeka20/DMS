package com.dms.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j

@OpenAPIDefinition(
		info = @Info(
				title = "Document Ingestion and Q&A", 
				description = ": Develop a backend application using Spring Boot (Java) to handle document ingestion, basic storage, and Basic retrieval Q&A REST API Documentation", 
				version = "v1.0", 
				contact = @Contact(
						name = "service desk", 
						email = "example@test.com"), 
				license = @License(
						name = "Apache 2.0", 
						url = "www.test.com")), 
		externalDocs = @ExternalDocumentation(
				description = "DMS doc", 
				url = "www.test.com")

		)
public class DmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

}
