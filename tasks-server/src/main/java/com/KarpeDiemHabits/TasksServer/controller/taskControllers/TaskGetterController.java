package com.KarpeDiemHabits.TasksServer.controller.taskControllers;

import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskGetterService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskGetterController {

    @Autowired
    TaskGetterService taskGetterService;

    @GetMapping( "/task/id/{id}" )
    public ResponseEntity < Task > getTaskById( @PathVariable (value = "id") Long id ){
      
        return taskGetterService.getTaskById( id )
            .map( tsk -> new ResponseEntity<>( tsk, HttpStatus.OK ))
            .orElse( new ResponseEntity<>( null, HttpStatus.NO_CONTENT));

    }

    @GetMapping( "/task/getall")
    public ArrayList < Task > getAllTask(){
        return taskGetterService.getAllTask();
    }

    @GetMapping( "/task/getbydate/{date}")
    public ResponseEntity <ArrayList<Task>> getTasksByDate( 
        @PathVariable ( value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ){
        
        return new ResponseEntity<ArrayList<Task>>( taskGetterService.getTasksByDate( date ) , HttpStatus.OK );
        
    }
}