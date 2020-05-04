package com.example.dsassignment.fireAlarmSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dsassignment.fireAlarmSystem.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	//custom method to retrieve the user details by passing email as a parameter
	User getUserByEmail(String email);

}
