package com.example.dsassignment.fireAlarmSystem.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dsassignment.fireAlarmSystem.model.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

	

	// custom query to retrieve all the ids of alarms in the DB
	@Query(value="SELECT id from alarm",nativeQuery = true) 
	 List<Integer> findIDs();
	
	
	// custom query to modify an existing record of an alarm in the DB
	@Transactional
	@Modifying
	@Query(value="UPDATE alarm a set a.co2level=?1,a.smoke_level=?2 WHERE a.id=?3",nativeQuery = true)
	void updateFields(int co2level,int smokelevel, int id);

}
