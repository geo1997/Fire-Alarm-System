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


@Service
public class AlarmService {
	@Autowired
	private AlarmRepository alarmRepository;


	public Alarm saveAlarm(Alarm alarm) {
		return alarmRepository.save(alarm);
	}

	public List<Alarm> getAlarms(){
		return alarmRepository.findAll();
	}

	public Alarm getAlarmById(int id){
		return alarmRepository.findById(id).orElse(null);
	}

	public void deleteAlarm(int id) {
		alarmRepository.deleteById(id);
		
	}
	
	public Alarm updateAlarm(Alarm alarm) {
		
		Alarm existingAlarm = alarmRepository.findById(alarm.getId()).orElse(null);
		existingAlarm.setFloorNum(alarm.getFloorNum());
		existingAlarm.setRoomNum(alarm.getRoomNum());
		existingAlarm.setSmokeLevel(alarm.getSmokeLevel());
		existingAlarm.setCo2level(alarm.getCo2level());
		
		return alarmRepository.save(existingAlarm);
	}
	
	
	public List<Integer> getIds(){
		return alarmRepository.findIDs();
	}
	
	public void updateFields(int co2, int smoke, int id) {
		 alarmRepository.updateFields(co2, smoke, id);
	}
	
	
}
