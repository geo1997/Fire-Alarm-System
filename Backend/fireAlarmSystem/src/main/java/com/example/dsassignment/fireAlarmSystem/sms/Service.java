package com.example.dsassignment.fireAlarmSystem.sms;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;;

@org.springframework.stereotype.Service
public class Service {

	private final SmsSender smsSender;
	
	@Autowired
	public Service(@Qualifier("twilio") TwilioSmsSender twilioSmsSender) {
		this.smsSender= twilioSmsSender;
	}
	
	public void smsSend(SmsRequest smsRequest) {
		smsSender.sendSms(smsRequest);
	}
	
}
