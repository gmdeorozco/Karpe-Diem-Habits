package com.KarpeDiemHabits.ChallengesServer.service.daylifeServices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.ChallengesServer.service.challengeServices.ChallengeGetterService;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;

@Service
public class DayLifeScoreService {

    @Autowired
    DayLifeRepository dayLifeRepository;

    @Autowired
    ChallengeRepository taskRepository;

    @Autowired
    ChallengeGetterService taskGetterService;



    public DayLifeScoreService(){}

    public double getScore( DayLife dayLife ){
        return ( 
            dayLife.getApprovedChallenges().size() * 100 ) / ( dayLife.getApprovedChallenges().size() 
                + dayLife.getChallenges().size() );
    }

    public DayLife approveTask( Long dayLifeId, Long challengeId ){
        Optional < DayLife > dayLife = dayLifeRepository.findById( dayLifeId );
        Optional < Challenge > task = taskRepository.findById( challengeId );
        
        if ( dayLife.isPresent() && task.isPresent() &&
            taskGetterService.isChallengeWithinDayLife( dayLife.get(), challengeId ) &&
            dayLife.get().getChallenges().remove( task.get() ) &&
            dayLife.get().getApprovedChallenges().add( task.get() )){
                return dayLifeRepository.save(dayLife.get());
            }
        return null;
    }

   
    public DayLife failTask(Long dayLifeId, Long challengeId) {
        Optional <DayLife> dayLife = dayLifeRepository.findById( dayLifeId );
        Optional <Challenge> task = taskRepository.findById( challengeId );
        
        if( dayLife.isPresent() && task.isPresent() &&
            taskGetterService.isChallengeWithinDayLife(dayLife.get(), challengeId) &&
            dayLife.get().getApprovedChallenges().remove(task.get()) &&
            dayLife.get().getChallenges().add(task.get()) ){
           
                return dayLifeRepository.save(dayLife.get());
        } else{
            return null;
        }

    }
}
