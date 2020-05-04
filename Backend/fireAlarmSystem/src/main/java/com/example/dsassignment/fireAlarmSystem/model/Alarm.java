package com.example.dsassignment.fireAlarmSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@AllArgsConstructor // initialize constructor with all arguments
@NoArgsConstructor // initialize constructor with no arguments
@Entity  // specify the class as an entity found in the DB
@Data // provide auto generated code for a normal class
@Table(name = "alarm") //table name found in the db
public class Alarm {

    @Id
    @GeneratedValue //id should be auto generated an auto incremented.
    private int id;

 

    private int floorNum;

    private int roomNum;

    private int smokeLevel;

    private int co2level;



}
