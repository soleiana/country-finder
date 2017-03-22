package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:country-finder.properties"})
public class CountryFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryFinderApplication.class, args);
	}
}
