package com.example.dsassignment.fireAlarmSystem.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration //automatically configures beans
@ConfigurationProperties("twilio") //automatically configures beans to twillio
@Data // provide auto generated code for a normal class
@NoArgsConstructor // initialize constructor with no arguments
public class TwilloConfiguration {
	
	private String accountSid;
	private String authToken;
	private String trialNumber;
	

}
