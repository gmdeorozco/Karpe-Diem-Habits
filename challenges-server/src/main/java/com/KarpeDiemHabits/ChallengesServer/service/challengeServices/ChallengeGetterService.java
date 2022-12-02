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
    
    public ArrayList<Challenge> getAllChallenges() {
        return (ArrayList<Challenge>) challengeRepository.findAll();
    }

    public Optional<Challenge> getChallengeById(long id) {
        return challengeRepository.findById(id);
    }

    public ArrayList<Challenge> getChallengesByDate( LocalDate date ) {
        ArrayList< Challenge > allChallenges = ( ArrayList< Challenge > ) challengeRepository.findAll();

        ArrayList< Challenge > challengesWithDate = ( ArrayList< Challenge > ) allChallenges.stream()
            .filter( challenge -> challengeContainsDate( challenge, date ) )
            .collect(Collectors.toList());

        return challengesWithDate;
    }

    public boolean challengeContainsDate( Challenge challenge, LocalDate date ){
        LocalDate challengeInitialDate = challenge.getInitialDate();
        LocalDate challengeEndDate = challenge.getEndDate();

        return ( challengeInitialDate.equals(date) 
            || challengeEndDate.equals(date) 
            || ( challengeInitialDate.isBefore( date ) && challengeEndDate.isAfter( date ) ));
    }

    public boolean isChallengeWithinDayLife( DayLife dayLife, Long challengeId ){

        boolean isOnChallenge = dayLife.getChallenges().stream()
        .anyMatch( challenge -> challenge.getId() == challengeId);

        boolean isOnApprovedChallenges = dayLife.getApprovedChallenges().stream()
        .anyMatch( challenge -> challenge.getId() == challengeId );

        return isOnChallenge || isOnApprovedChallenges;
            
    }

}
