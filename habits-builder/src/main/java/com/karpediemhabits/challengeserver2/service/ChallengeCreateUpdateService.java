package com.karpediemhabits.challengeserver2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karpediemhabits.challengeserver2.entities.Challenge;
import com.karpediemhabits.challengeserver2.repository.ChallengeRepository;

@Service
public class ChallengeCreateUpdateService {
    @Autowired
    ChallengeRepository challengeRepository;

    public Challenge createChallenge( Challenge challenge )
    {
        Challenge ch = challengeRepository.save( challenge );
        return ch;
    }

    public Challenge updateChallenge( Challenge challenge )
    {
       
        Challenge ch = challengeRepository.save( challenge );
        return ch;
    }

}
