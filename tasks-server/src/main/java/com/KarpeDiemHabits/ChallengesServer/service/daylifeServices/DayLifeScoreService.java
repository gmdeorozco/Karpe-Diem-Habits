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
            dayLife.getApprovedTasks().size() * 100 ) / ( dayLife.getApprovedTasks().size() 
                + dayLife.getTasks().size() );
    }

    public DayLife approveTask( Long dayLifeId, Long taskId ){
        Optional < DayLife > dayLife = dayLifeRepository.findById( dayLifeId );
        Optional < Challenge > task = taskRepository.findById( taskId );
        
        if ( dayLife.isPresent() && task.isPresent() &&
            taskGetterService.isChallengeWithinDayLife( dayLife.get(), taskId ) &&
            dayLife.get().getTasks().remove( task.get() ) &&
            dayLife.get().getApprovedTasks().add( task.get() )){
                return dayLifeRepository.save(dayLife.get());
            }
        return null;
    }

   
    public DayLife failTask(Long dayLifeId, Long taskId) {
        Optional <DayLife> dayLife = dayLifeRepository.findById( dayLifeId );
        Optional <Challenge> task = taskRepository.findById( taskId );
        
        if( dayLife.isPresent() && task.isPresent() &&
            taskGetterService.isChallengeWithinDayLife(dayLife.get(), taskId) &&
            dayLife.get().getApprovedTasks().remove(task.get()) &&
            dayLife.get().getTasks().add(task.get()) ){
           
                return dayLifeRepository.save(dayLife.get());
        } else{
            return null;
        }

    }
}
