package com.example.dsassignment.fireAlarmSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.model.User;
import com.example.dsassignment.fireAlarmSystem.service.UserService;

@CrossOrigin(origins="*") //allow access to all resources from any domain
@RestController //mark UserController class as a request handler
public class UserController {

	@Autowired //spring injects UserService when the UserController is created
	private UserService userService;
	
	@PostMapping("/addUser") //handles HTTP POST request to add a user
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	
	@GetMapping("/getUser/{email}") //handles HTTP GET request to get a user by passing email as the param.
	public User getUserByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email);
	}
}
