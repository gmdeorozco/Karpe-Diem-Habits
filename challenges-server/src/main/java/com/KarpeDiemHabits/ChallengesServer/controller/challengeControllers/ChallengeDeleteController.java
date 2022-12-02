package com.KarpeDiemHabits.ChallengesServer.controller.challengeControllers;

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
public class ChallengeDeleteController {

    @Autowired
    ChallengeDeleteService taskDeleteService;

    @Autowired
    DayLifeGetterService dayLifeGetterService;

    @Autowired
    ChallengeGetterService challengeGetterService;

    @DeleteMapping( "/daylife/delete/challenge/{dayLifeId}/{challengeId}" )
    public boolean deleteChallengeFromDayLife( @PathVariable( value = "challengeId" ) Long challengeId, 
                                    @PathVariable( value = "dayLifeId" ) Long dayLifeId ){

        Optional <Challenge> challenge = challengeGetterService.getChallengeById( challengeId );
        Optional <DayLife> dayLife = dayLifeGetterService.getDayLifeById( dayLifeId );

        return taskDeleteService.deleteChallengeFromDayLife(dayLife.get(), challenge.get());
    
    }

    @DeleteMapping( "/challenge/delete/{challengeId}" )
    public boolean deleteChallenge ( @PathVariable (value = "challengeId") Long challengeId ){
        
        return challengeGetterService.getChallengeById( challengeId )
            .map( challenge -> {
                return taskDeleteService.deleteChallengeFromAllDayLife( challenge );
            }).orElse(  false );      
    }


}
