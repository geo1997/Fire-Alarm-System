package com.example.dsassignment.fireAlarmSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;

   

    private String email;
    
    private String password;

//    @OneToMany
//    private Set<Alarm> alarm;

}
