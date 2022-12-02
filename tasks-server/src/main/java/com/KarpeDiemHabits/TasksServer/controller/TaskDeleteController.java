package com.KarpeDiemHabits.TasksServer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.service.HabitsBuilderService;
import com.KarpeDiemHabits.TasksServer.service.TaskDeleteService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskDeleteController {

    @Autowired
    TaskDeleteService taskDeleteService;

    @Autowired
    HabitsBuilderService habitsBuilderService;

    @DeleteMapping( "/daylife/delete/task/{dayLifeId}/{taskId}" )
    public boolean deleteTaskFromDayLife( @PathVariable( value = "taskId" ) Long taskId, 
                                    @PathVariable( value = "dayLifeId" ) Long dayLifeId ){

        Optional <Task> task = habitsBuilderService.getTaskById( taskId );
        Optional <DayLife> dayLife = habitsBuilderService.getDayLifeById( dayLifeId );

        return taskDeleteService.deleteTaskFromDayLife(dayLife.get(), task.get());
    
    }

    @DeleteMapping( "/task/delete/{taskId}" )
    public boolean deleteTask ( @PathVariable (value = "taskId") Long taskId ){
        
        return habitsBuilderService.getTaskById( taskId )
            .map( task -> {
                return taskDeleteService.deleteTaskFromAllDayLife( task );
            }).orElse(  false );      
    }


}
