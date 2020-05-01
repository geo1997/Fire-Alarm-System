package com.example.dsassignment.fireAlarmSystem.sms;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsRequest {

	@NotBlank
	private final String phoneNumber;
	
	@NotBlank
	private final String message;
}
