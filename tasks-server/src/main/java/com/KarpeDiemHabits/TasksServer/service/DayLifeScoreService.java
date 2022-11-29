package com.KarpeDiemHabits.TasksServer.service;

import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;

@Service
public class DayLifeScoreService {
    
    public DayLifeScoreService(){}

    public double getScore( DayLife dayLife ){
        return ( 
            dayLife.getApprovedTasks().size() * 100 ) / ( dayLife.getApprovedTasks().size() 
                + dayLife.getTasks().size() );
    }
}
