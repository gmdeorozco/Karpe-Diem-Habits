package com.KarpeDiemHabits.TasksServer.controller.taskControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.service.daylifeServices.DayLifeGetterService;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskCreateUpdateService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskCreateUpdateController {

    @Autowired
    DayLifeGetterService habitsBuilderService;

    @Autowired
    TaskCreateUpdateService taskCreateUpdateService;

    @PostMapping( "/task/create" )
    public ResponseEntity < Task > createTask (@RequestBody Task task){
       
        return new ResponseEntity<>(taskCreateUpdateService.saveTask(task) , HttpStatus.CREATED);
    }
    
    @PutMapping( "/task/update" )
    public ResponseEntity < Task > updateTask (@RequestBody Task newTask ){
        
        return taskCreateUpdateService.updateTask(newTask) != null 
            ? new ResponseEntity< Task >( newTask , HttpStatus.OK )
            :  new ResponseEntity<>( null , HttpStatus.NO_CONTENT);
    }
    
}
