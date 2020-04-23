package com.example.dsassignment.fireAlarmSystem.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.dsassignment.fireAlarmSystem.model.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

	

	//Alarm findAll(int id);
}
