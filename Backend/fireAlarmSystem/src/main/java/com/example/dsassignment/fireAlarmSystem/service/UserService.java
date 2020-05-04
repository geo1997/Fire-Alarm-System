package com.example.dsassignment.fireAlarmSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dsassignment.fireAlarmSystem.model.User;
import com.example.dsassignment.fireAlarmSystem.repository.UserRepository;

@Service //initialized as a service class
public class UserService {

	@Autowired //spring injects UserRepository when the USerService is created
	private UserRepository userRepository;
	
	//method to add user by passing a user type parameter to the DB and returning the saved user
	public User addUser(User newUser) {
		return userRepository.save(newUser);
	}
	
	//method to list out a unique user by passing an email and retreieving the user from DB
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
	
	
	
	
	
}
