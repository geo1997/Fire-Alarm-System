package com.example.dsassignment.fireAlarmSystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.model.MailRequest;
import com.example.dsassignment.fireAlarmSystem.model.MailResponse;
import com.example.dsassignment.fireAlarmSystem.service.EmailService;

@RestController //mark EmailController class as a request handler
public class EmailController {

	@Autowired //spring injects EmailService when the EmailController is created
	private EmailService emailService;
	
	
	@PostMapping("/sendEmail")  //handles HTTP POST request to send an Email
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		
		Map<String, Object> model = new HashMap<>();
		model.put("Name", request.getName());
		
		return emailService.sendEmail(request, model);
	}
	
	
}
