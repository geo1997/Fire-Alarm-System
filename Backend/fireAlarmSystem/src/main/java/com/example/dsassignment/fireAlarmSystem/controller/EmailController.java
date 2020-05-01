package com.example.dsassignment.fireAlarmSystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.email.dto.MailRequest;
import com.example.dsassignment.fireAlarmSystem.email.dto.MailResponse;
import com.example.dsassignment.fireAlarmSystem.email.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	
	@PostMapping("/sendEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		
		Map<String, Object> model = new HashMap<>();
		model.put("Name", request.getName());
		
		return emailService.sendEmail(request, model);
	}
	
	
}
