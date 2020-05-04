package com.example.dsassignment.fireAlarmSystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins="*")
@SpringBootApplication
public class FireAlarmSystemApplication {

	//main method which runs the spring boot application
	public static void main(String[] args) {
		SpringApplication.run(FireAlarmSystemApplication.class, args);
	}

}
