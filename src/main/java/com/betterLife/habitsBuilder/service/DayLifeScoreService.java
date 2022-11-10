package com.betterLife.habitsBuilder.service;

import org.springframework.stereotype.Service;

import com.betterLife.habitsBuilder.model.DayLife;

@Service
public class DayLifeScoreService {
    
    public DayLifeScoreService(){}

    public double getScore( DayLife dayLife ){
        return ( 
            dayLife.getApprovedTasks().size() * 100 ) / ( dayLife.getApprovedTasks().size() 
                + dayLife.getTasks().size() );
    }
}
