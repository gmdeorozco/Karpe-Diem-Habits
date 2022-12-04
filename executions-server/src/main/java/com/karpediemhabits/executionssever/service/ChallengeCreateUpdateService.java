package com.karpediemhabits.executionssever.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karpediemhabits.executionssever.entities.Challenge;
import com.karpediemhabits.executionssever.entities.Execution;
import com.karpediemhabits.executionssever.repository.ChallengeRepository;
import com.karpediemhabits.executionssever.repository.ExecutionRepository;

@Service
public class ChallengeCreateUpdateService {
    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    ExecutionRepository executionRepository;


    public void createExecutions( Challenge challenge, LocalDate ini, LocalDate end){

        ini = ini == null ? challenge.getInitialDate() : ini;
        end = end == null ? challenge.getEndDate() : end;

        for( LocalDate date = ini; 
            ( date.isBefore( end ) || date.isEqual( end ) ); 
            date = date.plusDays(1)){
                if ( challenge.dayOfWeekIsActive( date.getDayOfWeek()) ){

                    challenge.getExecutions().add(
                        executionRepository.save(
                            Execution.builder()
                            .challenge(challenge)
                            .date(date)
                            .time( challenge.getTime())
                            .build()
                        )
                        
                    );
                }
            }
    }

    public boolean deleteFutureExecutions( Challenge challenge ){
       List<Execution> futureExecutions = challenge.getExecutions().stream()
            .filter(ex -> ex.getDate().isAfter( LocalDate.now()) || ex.getDate().isEqual( LocalDate.now()))
            .collect(Collectors.toList());

            System.out.println("futureEx = "+ futureExecutions.size());

        return futureExecutions.stream()
            .allMatch(ex -> deleteExecutionFromChallenge(challenge,ex));
        
    }

    public boolean deleteExecutionFromChallenge( Challenge challenge, Execution execution ){
        challenge.getExecutions().remove(execution) ;
        executionRepository.deleteById(execution.getId());
        return true;
    }

    public double getScoreByDate(LocalDate date){
        Long approved = executionRepository.findByDate(date).stream()
            .filter( ex -> ex.isChallengeApproved())
            .count();

        Long totalEx = executionRepository.findByDate(date).stream()
            .count();
        
        return ( 100 * approved )/totalEx;
    }

    public boolean approve( Long executionId ) {
        Execution ex = executionRepository.findById(executionId).get(); 
        ex.setChallengeApproved(true);
        executionRepository.save(ex);

        return true;
         
    }

    public boolean fail( Long executionId ) {
        Execution ex = executionRepository.findById(executionId).get(); 
        ex.setChallengeApproved(false);
        executionRepository.save(ex);

        return true;
         
    }
}