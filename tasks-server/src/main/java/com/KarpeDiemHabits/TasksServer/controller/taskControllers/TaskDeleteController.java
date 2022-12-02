package com.KarpeDiemHabits.TasksServer.controller.taskControllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.service.daylifeServices.DayLifeGetterService;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskDeleteService;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskGetterService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskDeleteController {

    @Autowired
    TaskDeleteService taskDeleteService;

    @Autowired
    DayLifeGetterService dayLifeGetterService;

    @Autowired
    TaskGetterService taskGetterService;

    @DeleteMapping( "/daylife/delete/task/{dayLifeId}/{taskId}" )
    public boolean deleteTaskFromDayLife( @PathVariable( value = "taskId" ) Long taskId, 
                                    @PathVariable( value = "dayLifeId" ) Long dayLifeId ){

        Optional <Task> task = taskGetterService.getTaskById( taskId );
        Optional <DayLife> dayLife = dayLifeGetterService.getDayLifeById( dayLifeId );

        return taskDeleteService.deleteTaskFromDayLife(dayLife.get(), task.get());
    
    }

    @DeleteMapping( "/task/delete/{taskId}" )
    public boolean deleteTask ( @PathVariable (value = "taskId") Long taskId ){
        
        return taskGetterService.getTaskById( taskId )
            .map( task -> {
                return taskDeleteService.deleteTaskFromAllDayLife( task );
            }).orElse(  false );      
    }


}
