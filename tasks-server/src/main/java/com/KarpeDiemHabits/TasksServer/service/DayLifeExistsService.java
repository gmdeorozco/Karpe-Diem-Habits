package com.KarpeDiemHabits.TasksServer.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;

@Service
public class DayLifeExistsService {

    @Autowired
    HabitsBuilderService habitsBuilderService;


    public DayLifeExistsService(){

    }

   


    

    public boolean dayLifeExistsOnDate( LocalDate date ){
        ArrayList< DayLife > dayLifes = habitsBuilderService
            .getAllDayLife();
        
        return dayLifes.stream()
                        .anyMatch(daylife -> daylife.getDate().equals( date ));
    }

   
    

    




    

}
