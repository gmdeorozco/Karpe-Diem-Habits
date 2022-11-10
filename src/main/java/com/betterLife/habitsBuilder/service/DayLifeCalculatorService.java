package com.betterLife.habitsBuilder.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;

import lombok.extern.log4j.Log4j2;

@Service
public class DayLifeCalculatorService {

    @Autowired
    HabitsBuilderService habitsBuilderService;


    public DayLifeCalculatorService(){

    }

    public void recalculateDayLifes(Task task) {
        ArrayList< DayLife > relevantDayLifes = 
            createDayLifesByInterval(task, task.getInitialDate(), task.getEndDate());
        task.setDayLifes( relevantDayLifes );
        
        
    }

    public ArrayList<DayLife> createDayLifesByInterval(Task task, LocalDate initialDate, LocalDate endDate){
        
        ArrayList < DayLife > dayLifes = new ArrayList<>();
        
        for( LocalDate date = initialDate; 
                date.isBefore( endDate ) || date.isEqual( endDate ); 
                date = date.plusDays(1 ) ){

                    if ( !task.dayOfWeekIsActive(date.getDayOfWeek()) ) { continue; }
                    

                    DayLife existentDayLife = habitsBuilderService.getDayLifeByDate(date);

                    if ( existentDayLife == null ){
                        DayLife dl = new DayLife();
                        dl.setDate(date);
                        dl.setTasks(new ArrayList<>());
                        dl.getTasks().add(task);
                        dl = habitsBuilderService.saveDayLife( dl );
                        
                        dayLifes.add( dl );
                    }else{
                        
                        existentDayLife.getTasks().add(task);
                        habitsBuilderService.saveDayLife(existentDayLife);
                    }

                    


        }
        return dayLifes;
    }

    public boolean dayLifeExistsOnDate( LocalDate date ){
        ArrayList< DayLife > dayLifes = habitsBuilderService
            .getAllDayLife();
        
        return dayLifes.stream()
                        .anyMatch(daylife -> daylife.getDate().equals(date));
    }

    

    




    

}
