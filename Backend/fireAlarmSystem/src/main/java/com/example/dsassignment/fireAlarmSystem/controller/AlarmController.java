package com.example.dsassignment.fireAlarmSystem.controller;

import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.model.Alarm;
import com.example.dsassignment.fireAlarmSystem.service.AlarmService;

@RestController //mark AlarmController class as a request handler
public class AlarmController {

	@Autowired //spring injects AlarmService when the AlarmController is created
	private AlarmService alarmService;
	
	@PostMapping("/addAlarm") //handles HTTP POST request to add Alarm
	public Alarm addAlarm(@RequestBody Alarm alarm) {
		return alarmService.saveAlarm(alarm);
	}
	
	@GetMapping("/alarms")//handles HTTP GET request to get all Alarms
	public List<Alarm> findAllAlarms(){
		return alarmService.getAlarms();
	}
	
	
	@PutMapping("/updateAlarm")//handles HTTP PUT method to update an alarm
	public Alarm updateAlarm(@RequestBody Alarm alarm) {
		return alarmService.updateAlarm(alarm);
	}
	
	
	@DeleteMapping("/alarmDelete/{id}")//handles HTTP DELETE method to delete an alarm
	public void deleteAlarm(@PathVariable int id) {
		 alarmService.deleteAlarm(id);
	}
	
	
	@GetMapping("/getIDs")//handles HTTP GET request to get all Alarm ids
	public List<Integer> findAllIds(){
		return alarmService.getIds();
	}
	
	@PutMapping("/updateFields/{co2}/{smoke}/{id}")//handles HTTP PUT method to UPDATE particular details of an alarm
		public void updateFields(@PathVariable int co2,@PathVariable int smoke,@PathVariable int id){
		 alarmService.updateFields(co2, smoke, id);
	}
	
	
}
