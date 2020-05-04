package com.example.dsassignment.fireAlarmSystem.service;



import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dsassignment.fireAlarmSystem.model.Alarm;
import com.example.dsassignment.fireAlarmSystem.repository.AlarmRepository;


@Service //initialized as a service class
public class AlarmService {
	@Autowired //spring injects AlarmRepository when the AlarmService is created
	private AlarmRepository alarmRepository;


	//method to save alarm in the DB by passing an alarm and returning the saved alarm
	public Alarm saveAlarm(Alarm alarm) {
		return alarmRepository.save(alarm);
	}

	//method to retrieve all the alarms in the DB as an alarm List
	public List<Alarm> getAlarms(){
		return alarmRepository.findAll();
	}

	

	//method to delete an alarm from the DB by passing the alarm id
	public void deleteAlarm(int id) {
		alarmRepository.deleteById(id);
		
	}
	
	//method to update an alarm in the DB by passing an alarm and return the updated alarm
	public Alarm updateAlarm(Alarm alarm) {
		
		Alarm existingAlarm = alarmRepository.findById(alarm.getId()).orElse(null);
		existingAlarm.setFloorNum(alarm.getFloorNum());
		existingAlarm.setRoomNum(alarm.getRoomNum());
		existingAlarm.setSmokeLevel(alarm.getSmokeLevel());
		existingAlarm.setCo2level(alarm.getCo2level());
		
		return alarmRepository.save(existingAlarm);
	}
	
	
	//method to retrieve all the alarm ids in the DB
	public List<Integer> getIds(){
		return alarmRepository.findIDs();
	}
	
	//method to update some of the fields in the alarm table in the DB
	public void updateFields(int co2, int smoke, int id) {
		 alarmRepository.updateFields(co2, smoke, id);
	}
	
	
}
