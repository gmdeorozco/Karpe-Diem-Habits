package com.karpediemhabits.challengeserver2.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karpediemhabits.challengeserver2.entities.Execution;
import com.karpediemhabits.challengeserver2.repository.ExecutionRepository;
import com.karpediemhabits.challengeserver2.service.ChallengeCreateUpdateService;

@RestController
public class ExecutionController {

    @Autowired
    ExecutionRepository executionRepository;

    @Autowired
    ChallengeCreateUpdateService challengeCreateUpdateService;

    @GetMapping("/execution/bydate/{date}")
    public ResponseEntity< List< Execution> > getExecutionByDate( @PathVariable(value = "date") LocalDate date){
        return new ResponseEntity<List< Execution>>(executionRepository.findByDate(date), HttpStatus.OK);
    }

    @GetMapping("/execution/all")
    public List < Execution> getExecutions( ){
        return (List<Execution>) executionRepository.findAll();
    }

    @GetMapping("/execution/getscorebydate/{date}")
    public double getScoreByDate( @PathVariable(value="date") LocalDate date ){
        return challengeCreateUpdateService.getScoreByDate(date);
    }

    @PutMapping("/execution/approve/{executionId}")
    public boolean getScoreByDate( @PathVariable(value="executionId") Long executionId ){
        return challengeCreateUpdateService.approve( executionId );
    }
    
}
