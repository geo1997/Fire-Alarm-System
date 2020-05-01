package com.example.dsassignment.fireAlarmSystem.sms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

	private final TwilloConfiguration twillioConfg;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TwillioInitializer.class);
	
	@Autowired
	 public TwilioSmsSender(TwilloConfiguration twilloConfiguration) {
		// TODO Auto-generated constructor stub
		this.twillioConfg = twilloConfiguration;
	}
	
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
	
	private boolean isPhoneNumberValid(String phString) {
		return true;
	}
	
}
