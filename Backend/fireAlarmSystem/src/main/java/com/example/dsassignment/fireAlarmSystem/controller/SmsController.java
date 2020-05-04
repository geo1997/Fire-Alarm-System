package com.example.dsassignment.fireAlarmSystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.model.SmsRequest;
import com.example.dsassignment.fireAlarmSystem.service.TwilioService;
 
@RestController //mark SmsController class as a request handler
@RequestMapping("api/sms") //provide the url path to the service
public class SmsController {

	private final TwilioService service;
	
	@Autowired //an instance of service is injected to the constructor when SmsController is created.
	public SmsController(TwilioService service) {
	// TODO Auto-generated constructor stub
		this.service = service;
	}
	
	@PostMapping //handles HTTP POST request to send a sms
	public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
		service.smsSend(smsRequest);
	}
}
