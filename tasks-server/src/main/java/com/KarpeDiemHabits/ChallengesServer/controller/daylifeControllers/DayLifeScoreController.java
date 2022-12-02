package com.KarpeDiemHabits.ChallengesServer.controller.daylifeControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeScoreService;

@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")  
public class DayLifeScoreController {
    
    @Autowired
    DayLifeScoreService dayLifeScoreService;

    @PutMapping( "/daylife/approve/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > approveTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
       
        DayLife dl = dayLifeScoreService.approveTask(dayLifeId, taskId) ;

        return dl != null 
            ? new ResponseEntity< DayLife >( dl, HttpStatus.OK )
            : new ResponseEntity< >( null, HttpStatus.NO_CONTENT );

        }
    

    @PutMapping( "/daylife/fail/{dayLifeId}/{taskId}" )
    public ResponseEntity < DayLife > failTask( @PathVariable( value = "dayLifeId" ) Long dayLifeId, 
                                                    @PathVariable( value = "taskId" ) Long taskId ){
                DayLife dl = dayLifeScoreService.failTask(dayLifeId, taskId) ;
                return dl != null 
                       ? new ResponseEntity< DayLife >( dl, HttpStatus.OK )
                       : new ResponseEntity< >( null, HttpStatus.NO_CONTENT );
        
    }
}
