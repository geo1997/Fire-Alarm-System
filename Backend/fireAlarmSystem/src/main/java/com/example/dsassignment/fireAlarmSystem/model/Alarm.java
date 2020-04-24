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

    private int floor_num;

    private int room_num;

    private int smoke_level;

    private int co2level;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    private User user;

}
