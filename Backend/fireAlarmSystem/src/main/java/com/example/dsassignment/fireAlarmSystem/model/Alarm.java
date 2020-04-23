package com.example.dsassignment.fireAlarmSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue
    private int id;

    //private String alarmName;

    private int floorNum;

    private int roomNum;

    private int smokeLevel;

    private int co2level;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    private User user;

}
