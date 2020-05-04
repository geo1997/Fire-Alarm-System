package com.example.dsassignment.fireAlarmSystem.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.example.dsassignment.fireAlarmSystem.model.SmsRequest;
import com.example.dsassignment.fireAlarmSystem.sms.SmsSender;
import com.example.dsassignment.fireAlarmSystem.sms.TwilioSmsSender;;

@org.springframework.stereotype.Service
public class TwilioService {

	private final SmsSender smsSender;
	
	@Autowired 
	public TwilioService(@Qualifier("twilio") TwilioSmsSender twilioSmsSender) {
		this.smsSender= twilioSmsSender;
	}
	
	//method implemenation to send out a sms
	public void smsSend(SmsRequest smsRequest) {
		smsSender.sendSms(smsRequest);
	}
	
}
