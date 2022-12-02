package com.KarpeDiemHabits.ChallengesServer.controller.daylifeControllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeCreateUpdateService;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeDeleteService;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeGetterService;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeGetterService;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeScoreService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class DayLifeGetterController {

    @Autowired
    DayLifeGetterService dayLifeGetterService;

    @Autowired
    ChallengeDeleteService taskDeleteService;

    @Autowired
    ChallengeCreateUpdateService taskUpdateService;

    @Autowired
    ChallengeGetterService taskGetterService;

    @Autowired
    DayLifeScoreService dayLifeScoreService;

    

    @GetMapping("/daylife/id/{id}")
    public ResponseEntity < DayLife > getDaylifesById( @PathVariable (value = "id") Long id ){

        return dayLifeGetterService.getDayLifeById( id )
            .map( dl -> new ResponseEntity<>(dl, HttpStatus.OK ))
            .orElse( new ResponseEntity<>( null, HttpStatus.NO_CONTENT) );
    }

    @GetMapping( "/daylife/getall")
    public ArrayList < DayLife > getAllDayLifes(){
        return dayLifeGetterService.getAllDayLife();
    }

    @GetMapping( "/daylife/getscore/{dayLifeId}" )
    public ResponseEntity< Double > getDayLifeScore( @PathVariable( value = "dayLifeId") Long dayLifeId ){
        return dayLifeGetterService.getDayLifeById( dayLifeId ) 
            .map( dl -> new ResponseEntity< Double >( dayLifeScoreService.getScore( dl ), HttpStatus.OK ))
            .orElse( new ResponseEntity< Double >( -1.0 , HttpStatus.NO_CONTENT ));

    }

    
    
}
