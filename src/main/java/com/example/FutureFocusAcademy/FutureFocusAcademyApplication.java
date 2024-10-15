package com.example.FutureFocusAcademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.FutureFocusAcademy") // Ensure this is set correctly
public class FutureFocusAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureFocusAcademyApplication.class, args);
	}
}
