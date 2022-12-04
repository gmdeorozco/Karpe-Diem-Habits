package com.karpediemhabits.challengeserver2.repository;

import org.springframework.data.repository.CrudRepository;

import com.karpediemhabits.challengeserver2.entities.Challenge;

public interface ChallengeRepository extends CrudRepository <Challenge, Long> {
    
}
