package com.example.dsassignment.fireAlarmSystem.sms;

import com.example.dsassignment.fireAlarmSystem.model.SmsRequest;

public interface SmsSender {

	// abstract method to sendSms
	public void sendSms(SmsRequest smsRequest);
}
