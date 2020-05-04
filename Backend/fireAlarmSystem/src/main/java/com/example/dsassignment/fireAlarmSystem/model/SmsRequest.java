package com.example.dsassignment.fireAlarmSystem.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // provide auto generated code for a normal class
@AllArgsConstructor // initialize constructor with all arguments
public class SmsRequest {

	@NotBlank // the value cannot be null
	private final String phoneNumber;
	
	@NotBlank // the value cannot be null
	private final String message;
}
