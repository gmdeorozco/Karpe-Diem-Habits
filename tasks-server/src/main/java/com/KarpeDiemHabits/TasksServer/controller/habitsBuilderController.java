package com.KarpeDiemHabits.TasksServer.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.service.DayLifeCalculatorService;
import com.KarpeDiemHabits.TasksServer.service.DayLifeScoreService;
import com.KarpeDiemHabits.TasksServer.service.HabitsBuilderService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class habitsBuilderController {

    @Autowired
    HabitsBuilderService habitsBuilderService;

    @Autowired
    DayLifeCalculatorService dayLifeCalculatorService;



    @Autowired
    DayLifeScoreService dayLifeScoreService;



    @GetMapping( "/task/id/{id}" )
    public ResponseEntity < Task > getTaskById( @PathVariable (value = "id") Long id ){
      
        return habitsBuilderService.getTaskById( id )
            .map( tsk -> new ResponseEntity<>( tsk, HttpStatus.OK ))
            .orElse( new ResponseEntity<>( null, HttpStatus.NO_CONTENT));

    }

    @GetMapping( "/task/getall")
    public ArrayList < Task > getAllTask(){
        return habitsBuilderService.getAllTask();
    }

    @GetMapping( "/task/getbydate/{date}")
    public ResponseEntity <ArrayList<Task>> getTasksByDate( 
        @PathVariable ( value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ){
        
        return new ResponseEntity<ArrayList<Task>>( habitsBuilderService.getTasksByDate( date ) , HttpStatus.OK );
        
    }

    @PostMapping( "/task/create" )
    public ResponseEntity < Task > createTask (@RequestBody Task task){
       
        return new ResponseEntity<>(habitsBuilderService.saveTask(task) , HttpStatus.CREATED);
    }
    
    @PutMapping( "/task/update" )
    public ResponseEntity < Task > updateTask (@RequestBody Task newTask ){
        
        return habitsBuilderService.updateTask(newTask) != null 
            ? new ResponseEntity< Task >( newTask , HttpStatus.OK )
            :  new ResponseEntity<>( null , HttpStatus.NO_CONTENT);
    }

    @PutMapping( "/daylife/approve/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > approveTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
       
        DayLife dl = habitsBuilderService.approveTask(dayLifeId, taskId) ;

        return dl != null 
            ? new ResponseEntity< DayLife >( dl, HttpStatus.OK )
            : new ResponseEntity< >( null, HttpStatus.NO_CONTENT );

        }
    

    @PutMapping( "/daylife/fail/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > failTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
                                                        DayLife dl = habitsBuilderService.failTask(dayLifeId, taskId) ;

                return dl != null 
                       ? new ResponseEntity< DayLife >( dl, HttpStatus.OK )
                       : new ResponseEntity< >( null, HttpStatus.NO_CONTENT );
        
    }

    @GetMapping("/daylife/id/{id}")
    public ResponseEntity < DayLife > getDaylifesById( @PathVariable (value = "id") Long id ){

        return habitsBuilderService.getDayLifeById( id )
            .map( dl -> new ResponseEntity<>(dl, HttpStatus.OK ))
            .orElse( new ResponseEntity<>( null, HttpStatus.NO_CONTENT) );
    }

    @GetMapping( "/daylife/getall")
    public ArrayList < DayLife > getAllDayLifes(){
        return habitsBuilderService.getAllDayLife();
    }

    @GetMapping( "/daylife/getscore/{dayLifeId}" )
    public ResponseEntity< Double > getDayLifeScore( @PathVariable( value = "dayLifeId") Long dayLifeId ){
        return habitsBuilderService.getDayLifeById( dayLifeId ) 
            .map( dl -> new ResponseEntity< Double >( dayLifeScoreService.getScore( dl ), HttpStatus.OK ))
            .orElse( new ResponseEntity< Double >( -1.0 , HttpStatus.NO_CONTENT ));

    }

    @DeleteMapping( "/daylife/delete/task/{dayLifeId}/{taskId}" )
    public boolean deleteTaskFromDayLife( @PathVariable( value = "taskId" ) Long taskId, 
                                    @PathVariable( value = "dayLifeId" ) Long dayLifeId ){

        Optional <Task> task = habitsBuilderService.getTaskById( taskId );
        Optional <DayLife> dayLife = habitsBuilderService.getDayLifeById( dayLifeId );

        return habitsBuilderService.deleteTaskFromDayLife(dayLife.get(), task.get());
    
    }

    @DeleteMapping( "/task/delete/{taskId}" )
    public boolean deleteTask ( @PathVariable (value = "taskId") Long taskId ){
        
        return habitsBuilderService.getTaskById( taskId )
            .map( task -> {
                return habitsBuilderService.deleteTaskFromAllDayLife( task );
            }).orElse(  false );      
    }
    
}
