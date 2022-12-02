package com.KarpeDiemHabits.ChallengesServer.controller.challengeControllers;

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
public class ChallengeCreateUpdateController {


    @Autowired
    ChallengeCreateUpdateService challengeCreateUpdateService;

    @PostMapping( "/challenge/create" )
    public ResponseEntity < Challenge > createChallenge (@RequestBody Challenge challenge){
       
        return new ResponseEntity<>(challengeCreateUpdateService.saveChallenge(challenge) , HttpStatus.CREATED);
    }
    
    @PutMapping( "/challenge/update" )
    public ResponseEntity < Challenge > updateChallenge (@RequestBody Challenge newChallenge ){
        
        return challengeCreateUpdateService.updateChallenge(newChallenge) != null 
            ? new ResponseEntity< Challenge >( newChallenge , HttpStatus.OK )
            :  new ResponseEntity<>( null , HttpStatus.NO_CONTENT);
    }
    
}
