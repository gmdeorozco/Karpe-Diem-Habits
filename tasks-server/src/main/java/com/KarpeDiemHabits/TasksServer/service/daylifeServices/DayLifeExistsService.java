package com.KarpeDiemHabits.TasksServer.service.daylifeServices;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;

@Service
public class DayLifeExistsService {

    @Autowired
    DayLifeGetterService habitsBuilderService;


    public DayLifeExistsService(){

    }

   


    

    public boolean dayLifeExistsOnDate( LocalDate date ){
        ArrayList< DayLife > dayLifes = habitsBuilderService
            .getAllDayLife();
        
        return dayLifes.stream()
                        .anyMatch(daylife -> daylife.getDate().equals( date ));
    }

   
    

    




    

}
