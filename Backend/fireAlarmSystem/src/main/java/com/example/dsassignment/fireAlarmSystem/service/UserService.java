package com.example.dsassignment.fireAlarmSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dsassignment.fireAlarmSystem.model.User;
import com.example.dsassignment.fireAlarmSystem.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User addUser(User newUser) {
		return userRepository.save(newUser);
	}
	
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
	
	
	
	
	
}
