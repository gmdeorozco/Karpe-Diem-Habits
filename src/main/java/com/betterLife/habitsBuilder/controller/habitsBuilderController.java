package com.betterLife.habitsBuilder.controller;

import java.time.LocalDate;
import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;
import com.betterLife.habitsBuilder.service.DayLifeCalculatorService;
import com.betterLife.habitsBuilder.service.DayLifeScoreService;
import com.betterLife.habitsBuilder.service.HabitsBuilderService;
import com.betterLife.habitsBuilder.service.OrphanDayLifeService;
import com.betterLife.habitsBuilder.service.TaskFindAndDeleteService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class habitsBuilderController {

    @Autowired
    HabitsBuilderService habitsBuilderService;

    @Autowired
    DayLifeCalculatorService dayLifeCalculatorService;

    @Autowired
    TaskFindAndDeleteService taskFindAndDeleteService;

    @Autowired
    OrphanDayLifeService orphanDayLifeService;

    @Autowired
    DayLifeScoreService dayLifeScoreService;


    @GetMapping("/sayHi")
    public String sayHi(){
        return "Hi Habits Builder!";
    }

    @GetMapping( "/task/id/{id}" )
    public ResponseEntity < Task > getTaskById( @PathVariable (value = "id") Long id ){
        
        Task task = habitsBuilderService.getTaskById( id ).map( tsk -> {
            return tsk;
        }).orElse( null );
         
        return task == null ? new ResponseEntity<>( null, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>( task , HttpStatus.OK);      
    }

    @GetMapping( "/task/getall")
    public ArrayList < Task > getAllTasks(){
        return habitsBuilderService.getAllTask();
    }

    @GetMapping( "/task/getbydate/{date}")
    public ResponseEntity <ArrayList<Task>> getTasksByDate( 
        @PathVariable ( value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ){
        
        return new ResponseEntity<ArrayList<Task>>( habitsBuilderService.getTasksByDate( date ) , HttpStatus.OK );
        
    }

    @PostMapping( "/task/create" )
    public ResponseEntity < Task > createTask (@RequestBody Task task){
        
        dayLifeCalculatorService.recalculateDayLifes(habitsBuilderService.saveTask(task));

        return new ResponseEntity<>(task , HttpStatus.CREATED);
    }
    
    @PutMapping( "/task/update/{taskId}" )
    public ResponseEntity < Task > updateTask (@RequestBody Task newTask, @PathVariable(value = "taskId") Long taskId ){
        Task task = habitsBuilderService.getTaskById( taskId ).get();

        taskFindAndDeleteService.deleteTaskFromAllDayLife( task );
        dayLifeCalculatorService.recalculateDayLifes(habitsBuilderService.saveTask( newTask ));

        return new ResponseEntity<>(task , HttpStatus.CREATED);
    }

    @DeleteMapping( "/task/delete/{taskId}" )
    public boolean deleteTask ( @PathVariable (value = "taskId") Long taskId ){
        
        Task task = habitsBuilderService.getTaskById( taskId ).get();
       
        boolean deleteAllDayLifesFromTask = taskFindAndDeleteService.deleteTaskFromAllDayLife( task );
        if ( deleteAllDayLifesFromTask ) {
            habitsBuilderService.saveTask( task );
            habitsBuilderService.deleteTaskById( task.getId() );
        }

        return deleteAllDayLifesFromTask;
    }

   

    @PutMapping( "/daylife/approve/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > approveTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();
        Task task = habitsBuilderService.getTaskById( taskId ).get();
        
        if( taskFindAndDeleteService.findTaskWithinDayLife( dayLife, taskId )){
            
            dayLife.getTasks().remove( task );
            dayLife.getApprovedTasks().add( task );
            
        }

        return new ResponseEntity(habitsBuilderService.saveDayLife( dayLife ), HttpStatus.OK);
    }

    @PutMapping( "/daylife/fail/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > removeTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();
        Task task = habitsBuilderService.getTaskById( taskId ).get();
        
        if( taskFindAndDeleteService.findTaskWithinDayLife(dayLife, taskId)){
            dayLife.getApprovedTasks().remove(task);
            dayLife.getTasks().add(task);
            
        }

        return new ResponseEntity(habitsBuilderService.saveDayLife(dayLife), HttpStatus.OK);
    }

    @GetMapping("/daylife/id/{id}")
    public ResponseEntity < DayLife > getDaylifesById( @PathVariable (value = "id") Long id ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( id ).map( dl -> {
            return dl;
        }).orElse( null );
         
        return dayLife == null ? new ResponseEntity<>( null, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>( dayLife , HttpStatus.OK);
    }

    @GetMapping( "/daylife/getall")
    public ArrayList < DayLife > getAllDayLifes(){
        return habitsBuilderService.getAllDayLife();
    }

    @GetMapping( "/daylife/getscore/{dayLifeId}" )
    public double getDayLifeScore( @PathVariable( value = "dayLifeId") Long dayLifeId ){
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();

        return dayLifeScoreService.getScore( dayLife );
    }

    @DeleteMapping( "/daylife/delete/task/{dayLifeId}/{taskId}" )
    public boolean deleteTaskById( @PathVariable( value = "taskId" ) Long taskId, 
                                    @PathVariable( value = "dayLifeId" ) Long dayLifeId ){

        Task task = habitsBuilderService.getTaskById( taskId ).get();
        DayLife dayLife = habitsBuilderService.getDayLifeById( dayLifeId ).get();

        boolean delete = taskFindAndDeleteService.deleteTaskFromDayLife(dayLife, task);
        
        if ( delete ){ 
            habitsBuilderService.saveDayLife( dayLife );
            orphanDayLifeService.deleteOrphanDayLife( dayLife );
        }

        return delete;
    }
    
    
}
