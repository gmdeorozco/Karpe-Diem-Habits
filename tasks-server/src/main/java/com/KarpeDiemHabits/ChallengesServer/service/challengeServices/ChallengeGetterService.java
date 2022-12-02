package com.KarpeDiemHabits.ChallengesServer.service.challengeServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;

@Service
public class ChallengeGetterService {

    @Autowired
    ChallengeRepository challengeRepository;
    
    public ArrayList<Challenge> getAllTask() {
        return (ArrayList<Challenge>) challengeRepository.findAll();
    }

    public Optional<Challenge> getTaskById(long id) {
        return challengeRepository.findById(id);
    }

    public ArrayList<Challenge> getChallengesByDate( LocalDate date ) {
        ArrayList< Challenge > allTasks = ( ArrayList< Challenge > ) challengeRepository.findAll();

        ArrayList< Challenge > tasksWithDate = ( ArrayList< Challenge > ) allTasks.stream()
            .filter( task -> challengeContainsDate( task, date ) )
            .collect(Collectors.toList());

        return tasksWithDate;
    }

    public boolean challengeContainsDate( Challenge task, LocalDate date ){
        LocalDate taskInitialDate = task.getInitialDate();
        LocalDate taskEndDate = task.getEndDate();

        return ( taskInitialDate.equals(date) 
            || taskEndDate.equals(date) 
            || ( taskInitialDate.isBefore( date ) && taskEndDate.isAfter( date ) ));
    }

    public boolean isChallengeWithinDayLife( DayLife dayLife, Long challengeId ){

        boolean isOnChallenge = dayLife.getTasks().stream()
        .anyMatch( challenge -> challenge.getId() == challengeId);

        boolean isOnApprovedChallenges = dayLife.getApprovedTasks().stream()
        .anyMatch( challenge -> challenge.getId() == challengeId );

        return isOnChallenge || isOnApprovedChallenges;
            
    }

}
