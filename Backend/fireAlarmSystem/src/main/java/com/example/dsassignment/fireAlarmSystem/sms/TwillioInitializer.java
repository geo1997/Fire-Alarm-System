package com.example.dsassignment.fireAlarmSystem.sms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;



@Configuration
public class TwillioInitializer {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TwillioInitializer.class);
	private final TwilloConfiguration twilloConfiguration;
	
	@Autowired
	public TwillioInitializer(TwilloConfiguration twilloConfiguration) {
		this.twilloConfiguration = twilloConfiguration;
		Twilio.init(twilloConfiguration.getAccountSid(),
				twilloConfiguration.getAuthToken());
		
		LOGGER.info("Twillio initilaized with "+twilloConfiguration.getAccountSid());
	}
	
	

}
