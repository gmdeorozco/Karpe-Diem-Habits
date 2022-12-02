package com.KarpeDiemHabits.ChallengesServer.service.challengeServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeCreateUpdateService;

@Service
public class ChallengeCreateUpdateService {
    
    @Autowired
    ChallengeGetterService taskGetterService;

    @Autowired
    ChallengeDeleteService taskDeleteService;

    @Autowired
    ChallengeRepository taskRepository;

    @Autowired
    DayLifeCreateUpdateService dayLifeCreateUpdateService;

    public Challenge saveChallenge(Challenge t) {

        dayLifeCreateUpdateService.recalculateDayLifes(taskRepository.save(t));
        return t;
    }
    

    public Challenge updateChallenge(Challenge newChallenge ) {
        return taskGetterService.getChallengeById( newChallenge .getId() )
            .map(  chll  ->  {
                taskDeleteService.deleteTaskFromFutureDayLifes( chll );
                dayLifeCreateUpdateService.recalculateFutureDayLifes( taskRepository.save( newChallenge  ) );
                return newChallenge ;
            }).orElse(  null );
    }
    
}
