package com.KarpeDiemHabits.ChallengesServer.controller.taskControllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeDeleteService;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeGetterService;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeGetterService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class TaskDeleteController {

    @Autowired
    ChallengeDeleteService taskDeleteService;

    @Autowired
    DayLifeGetterService dayLifeGetterService;

    @Autowired
    ChallengeGetterService challengeGetterService;

    @DeleteMapping( "/daylife/delete/task/{dayLifeId}/{taskId}" )
    public boolean deleteTaskFromDayLife( @PathVariable( value = "taskId" ) Long taskId, 
                                    @PathVariable( value = "dayLifeId" ) Long dayLifeId ){

        Optional <Challenge> task = challengeGetterService.getTaskById( taskId );
        Optional <DayLife> dayLife = dayLifeGetterService.getDayLifeById( dayLifeId );

        return taskDeleteService.deleteTaskFromDayLife(dayLife.get(), task.get());
    
    }

    @DeleteMapping( "/task/delete/{taskId}" )
    public boolean deleteTask ( @PathVariable (value = "taskId") Long taskId ){
        
        return challengeGetterService.getTaskById( taskId )
            .map( task -> {
                return taskDeleteService.deleteTaskFromAllDayLife( task );
            }).orElse(  false );      
    }


}
