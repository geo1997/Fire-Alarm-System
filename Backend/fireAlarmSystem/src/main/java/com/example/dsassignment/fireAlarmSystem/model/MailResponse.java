package com.example.dsassignment.fireAlarmSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // provide auto generated code for a normal class
@AllArgsConstructor  // initialize constructor with all arguments
@NoArgsConstructor // initialize constructor with no arguments
public class MailResponse {

	private String message;
	private boolean status;
}
