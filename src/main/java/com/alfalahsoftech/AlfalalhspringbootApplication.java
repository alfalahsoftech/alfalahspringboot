package com.alfalahsoftech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class AlfalalhspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfalalhspringbootApplication.class, args);
	}

	@GetMapping("/")
	public String adminHome() {
		return "hi";
	}
}
