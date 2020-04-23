package com.example.dsassignment.fireAlarmSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dsassignment.fireAlarmSystem.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User getUserByEmail(String email);

}
