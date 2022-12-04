package com.karpediemhabits.challengeserver2.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karpediemhabits.challengeserver2.entities.Challenge;
import com.karpediemhabits.challengeserver2.entities.Execution;
import com.karpediemhabits.challengeserver2.repository.ChallengeRepository;
import com.karpediemhabits.challengeserver2.repository.ExecutionRepository;

@Service
public class ChallengeCreateUpdateService {
    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    ExecutionRepository executionRepository;

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
