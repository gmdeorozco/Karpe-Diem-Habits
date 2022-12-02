package com.KarpeDiemHabits.ChallengesServer.controller.taskControllers;

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

import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeGetterService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskGetterController {

    @Autowired
    ChallengeGetterService taskGetterService;

    @GetMapping( "/task/id/{id}" )
    public ResponseEntity < Challenge > getTaskById( @PathVariable (value = "id") Long id ){
      
        return taskGetterService.getTaskById( id )
            .map( tsk -> new ResponseEntity<>( tsk, HttpStatus.OK ))
            .orElse( new ResponseEntity<>( null, HttpStatus.NO_CONTENT));

    }

    @GetMapping( "/task/getall")
    public ArrayList < Challenge > getAllTask(){
        return taskGetterService.getAllTask();
    }

    @GetMapping( "/task/getbydate/{date}")
    public ResponseEntity <ArrayList<Challenge>> getTasksByDate( 
        @PathVariable ( value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date ){
        
        return new ResponseEntity<ArrayList<Challenge>>( taskGetterService.getChallengesByDate( date ) , HttpStatus.OK );
        
    }
}