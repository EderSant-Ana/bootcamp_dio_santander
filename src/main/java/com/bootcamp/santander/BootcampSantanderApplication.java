package com.bootcamp.santander;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class BootcampSantanderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootcampSantanderApplication.class, args);
	}
	
	@Bean
	public OpenAPI customOpenAPI(@Value("${application.description}") String description,
				                 @Value("${application.version}") String version,
								 @Value("${application.terms}") String terms) {
		return new OpenAPI().info(new Info()
				.title(description)
				.version(version)
				.termsOfService(terms)
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
	
	//url = http://localhost:8080/bootcamp_santander/swagger-ui.html

}
