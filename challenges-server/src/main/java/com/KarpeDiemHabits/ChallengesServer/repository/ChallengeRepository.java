package com.KarpeDiemHabits.ChallengesServer.repository;

import org.springframework.data.repository.CrudRepository;

import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;

public interface ChallengeRepository extends CrudRepository < Challenge, Long >{
    
}
