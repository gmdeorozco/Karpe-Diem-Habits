package com.KarpeDiemHabits.TasksServer.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.Task;

@Service
public class TaskFinderByDateService {
    public TaskFinderByDateService(){}

    //CHECK IF TASK CONTAINS  A DATE
    public boolean taskContainsDate( Task task, LocalDate date ){
        LocalDate taskInitialDate = task.getInitialDate();
        LocalDate taskEndDate = task.getEndDate();

        return ( taskInitialDate.equals(date) 
            || taskEndDate.equals(date) 
            || ( taskInitialDate.isBefore( date ) && taskEndDate.isAfter( date ) ));
    }
}
