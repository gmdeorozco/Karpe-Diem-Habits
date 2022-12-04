package com.karpediemhabits.challengeserver2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karpediemhabits.challengeserver2.entities.Challenge;
import com.karpediemhabits.challengeserver2.repository.ChallengeRepository;
import com.karpediemhabits.challengeserver2.service.ChallengeCreateUpdateService;

@RestController
public class ChallengeController {
    
    @Autowired 
    ChallengeRepository challengeRepository;

    @Autowired
    ChallengeCreateUpdateService challengeCreateUpdateService;

    @PostMapping("/challenge/create")
    public ResponseEntity<Challenge> createChallenge( @RequestBody Challenge challenge ){
        return new ResponseEntity<Challenge>(challengeCreateUpdateService.saveChallenge(challenge),HttpStatus.CREATED);
    }

    @PutMapping("/challenge/update")
    public ResponseEntity<Challenge> updateChallenge( @RequestBody Challenge challenge ){
        return new ResponseEntity<Challenge>(challengeCreateUpdateService.updateChallenge(challenge),HttpStatus.ACCEPTED);
    }

    @GetMapping("/challenge/getbyid/{challengeid}")
    public ResponseEntity<Challenge> getChallengeById( @PathVariable(value = "challengeid") Long challengeId ){
        return new ResponseEntity<Challenge>(challengeRepository.findById(challengeId).get(), HttpStatus.FOUND);
    }

    @DeleteMapping("/challenge/deletebyid/{challengeid}")
    public void deleteChallenge( @PathVariable(value = "challengeid") Long challengeId ){
        challengeRepository.deleteById(challengeId);
        
    }

    
}
