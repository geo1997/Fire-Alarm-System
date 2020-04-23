package com.example.dsassignment.fireAlarmSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.model.Alarm;
import com.example.dsassignment.fireAlarmSystem.service.AlarmService;

@RestController
public class AlarmController {

	@Autowired
	private AlarmService alarmService;
	
	@PostMapping("/addAlarm")
	public Alarm addAlarm(@RequestBody Alarm alarm) {
		return alarmService.saveAlarm(alarm);
	}
	
	@GetMapping("/alarms")
	public List<Alarm> findAllAlarms(){
		return alarmService.getAlarms();
	}
	
	@GetMapping("/alarms/{id}")
	public Alarm findAlarmById(@PathVariable int id) {
		return alarmService.getAlarmById(id);
	}
	
	@PostMapping("/updateAlarm")
	public Alarm updateAlarm(@RequestBody Alarm alarm) {
		return alarmService.updateAlarm(alarm);
	}
	
	
	@DeleteMapping("/alarmDelete/{id}")
	public String deleteAlarm(@PathVariable int id) {
		return alarmService.deleteAlarm(id);
	}
	
	
}
