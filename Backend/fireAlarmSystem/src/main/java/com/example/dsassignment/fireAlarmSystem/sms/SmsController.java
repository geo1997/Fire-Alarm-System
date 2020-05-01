package com.example.dsassignment.fireAlarmSystem.sms;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sms")
public class SmsController {

	private final Service service;
	
	@Autowired
	public SmsController(Service service) {
	// TODO Auto-generated constructor stub
		this.service = service;
	}
	
	@PostMapping
	public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
		service.smsSend(smsRequest);
	}
}
