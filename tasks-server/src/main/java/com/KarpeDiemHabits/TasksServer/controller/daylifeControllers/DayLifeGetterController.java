package com.KarpeDiemHabits.TasksServer.controller.daylifeControllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.service.daylifeServices.DayLifeGetterService;
import com.KarpeDiemHabits.TasksServer.service.daylifeServices.DayLifeScoreService;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskCreateUpdateService;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskDeleteService;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskGetterService;


@CrossOrigin( origins = "*")
@RestController
@RequestMapping("/api")
public class DayLifeGetterController {

    @Autowired
    DayLifeGetterService habitsBuilderService;

    @Autowired
    TaskDeleteService taskDeleteService;

    @Autowired
    TaskCreateUpdateService taskUpdateService;

    @Autowired
    TaskGetterService taskGetterService;

    @Autowired
    DayLifeScoreService dayLifeScoreService;

    

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

    
    
}
