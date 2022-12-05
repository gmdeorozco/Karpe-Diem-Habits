package com.karpediemhabits.executionssever.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karpediemhabits.executionssever.entities.Execution;
import com.karpediemhabits.executionssever.repository.ExecutionRepository;

@Service
public class ExecutionService {


    @Autowired
    ExecutionRepository executionRepository;


    public void createExecutions( LocalDate ini, LocalDate end, Long challengeId, LocalTime time, 
                        boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
                        boolean saturday, boolean sunday ){

      
        for( LocalDate date = ini; 
        ( date.isBefore( end ) || date.isEqual( end ) ); 
        date = date.plusDays( 1 ) ){
        
            if( ( date.getDayOfWeek() == DayOfWeek.MONDAY && monday )
                    || ( date.getDayOfWeek() == DayOfWeek.TUESDAY && tuesday )
                    || ( date.getDayOfWeek() == DayOfWeek.WEDNESDAY && wednesday ) 
                    || ( date.getDayOfWeek() == DayOfWeek.THURSDAY && thursday )
                    || ( date.getDayOfWeek() == DayOfWeek.FRIDAY && friday )
                    || ( date.getDayOfWeek() == DayOfWeek.SATURDAY && saturday )
                    || ( date.getDayOfWeek() == DayOfWeek.SUNDAY && sunday )
            ){
                executionRepository.save(
                        Execution.builder()
                        //.challengeId( challengeId )
                        .date( date )
                        .time( time )
                        .build()
                    );
            }
                    
              
            
        }
       
        
    }

    public boolean deleteFutureExecutions( Long challengeId ){

       List<Execution> futureExecutions = ((List<Execution>) executionRepository.findAll()).stream()

           // .filter(ex -> ex.getChallengeId() == challengeId)
            .filter(ex -> ex.getDate().isAfter( LocalDate.now()) || ex.getDate().isEqual( LocalDate.now()))
            .collect(Collectors.toList());

            System.out.println("futureEx = "+ futureExecutions.size());
            
            futureExecutions.stream()
            .forEach( ex -> executionRepository.deleteById(ex.getId() ));
        return true;
        
    }

    public boolean deleteExecutionFromChallenge( Execution execution ){
        
        executionRepository.deleteById(execution.getId());
        return true;
    }

    public double getScoreByDate(LocalDate date){
        Long approved = executionRepository.findByDate(date).stream()
            .filter( ex -> ex.isChallengeApproved())
            .count();

        Long totalEx = executionRepository.findByDate(date).stream()
            .count();
        
        return ( 100 * approved ) / totalEx;
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