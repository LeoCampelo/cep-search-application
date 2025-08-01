package com.cepsearch.cep_search_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CepSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CepSearchApplication.class, args);
	}

}
