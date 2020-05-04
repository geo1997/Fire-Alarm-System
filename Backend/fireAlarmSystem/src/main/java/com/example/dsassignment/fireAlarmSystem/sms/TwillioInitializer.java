package com.example.dsassignment.fireAlarmSystem.sms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.dsassignment.fireAlarmSystem.model.TwilloConfiguration;
import com.twilio.Twilio;



@Configuration //automatically configures beans
public class TwillioInitializer {
	
	//add logging support to the REST app
	private final static Logger LOGGER = LoggerFactory.getLogger(TwillioInitializer.class);
	
	//reference 
	private final TwilloConfiguration twilloConfiguration;
	
	/*
	 * Twilio init method is called to get the account sid and the authentication token
	 * in the application.properties
	 */
	@Autowired
	public TwillioInitializer(TwilloConfiguration twilloConfiguration) {
		this.twilloConfiguration = twilloConfiguration;
		Twilio.init(twilloConfiguration.getAccountSid(),
				twilloConfiguration.getAuthToken());
		
		LOGGER.info("Twillio initilaized with "+twilloConfiguration.getAccountSid());
	}
	
	

}
