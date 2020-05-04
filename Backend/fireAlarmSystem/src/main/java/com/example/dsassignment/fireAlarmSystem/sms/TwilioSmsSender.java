package com.example.dsassignment.fireAlarmSystem.sms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dsassignment.fireAlarmSystem.model.SmsRequest;
import com.example.dsassignment.fireAlarmSystem.model.TwilloConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("twilio") //is a service class
public class TwilioSmsSender implements SmsSender {

	//reference
	private final TwilloConfiguration twillioConfg;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TwillioInitializer.class);
	
	@Autowired
	 public TwilioSmsSender(TwilloConfiguration twilloConfiguration) {
		// TODO Auto-generated constructor stub
		this.twillioConfg = twilloConfiguration;
	}
	
	//method sendSms implementation
	/*
	 * checks if the method is valid and if so  the receiver phone number is requested 
	 * and then the sender number is requested ,net the message to sent is requested 
	 * and then the message is created.
	 */
	

	@Override
	public void sendSms(SmsRequest smsRequest) {
		// TODO Auto-generated method stub
		if(isPhoneNumberValid(smsRequest.getPhoneNumber())){
		PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
		PhoneNumber from = new PhoneNumber(twillioConfg.getTrialNumber());
		String message = smsRequest.getMessage();
		MessageCreator creator = Message.creator(to, from, message);
		creator.create();
		LOGGER.info("Send sms" +smsRequest);
	
		}else {
			throw new IllegalArgumentException(
					"phone number ["+smsRequest.getPhoneNumber()+" not valid");
		}
	
		
	}
	
	//method to check the validit of the phone number
	private boolean isPhoneNumberValid(String phString) {
		return true;
	}
	
}
