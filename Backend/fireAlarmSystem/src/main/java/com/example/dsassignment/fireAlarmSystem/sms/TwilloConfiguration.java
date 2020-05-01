package com.example.dsassignment.fireAlarmSystem.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties("twilio")
@Data
@NoArgsConstructor
public class TwilloConfiguration {
	
	private String accountSid;
	private String authToken;
	private String trialNumber;
	

}
