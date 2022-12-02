package com.KarpeDiemHabits.ChallengesServer.controller.taskControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeCreateUpdateService;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeGetterService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskCreateUpdateController {

    @Autowired
    DayLifeGetterService habitsBuilderService;

    @Autowired
    ChallengeCreateUpdateService taskCreateUpdateService;

    @PostMapping( "/task/create" )
    public ResponseEntity < Challenge > createTask (@RequestBody Challenge task){
       
        return new ResponseEntity<>(taskCreateUpdateService.saveTask(task) , HttpStatus.CREATED);
    }
    
    @PutMapping( "/task/update" )
    public ResponseEntity < Challenge > updateTask (@RequestBody Challenge newTask ){
        
        return taskCreateUpdateService.updateTask(newTask) != null 
            ? new ResponseEntity< Challenge >( newTask , HttpStatus.OK )
            :  new ResponseEntity<>( null , HttpStatus.NO_CONTENT);
    }
    
}
