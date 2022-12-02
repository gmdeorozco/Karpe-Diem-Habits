package com.KarpeDiemHabits.ChallengesServer.service.challengeServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;
import com.KarpeDiemHabits.ChallengesServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeCreateUpdateService;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeGetterService;

@Service
public class ChallengeDeleteService {
    
    @Autowired
    DayLifeGetterService habitsBuilderService;

    @Autowired
    DayLifeCreateUpdateService dayLifeCreateUpdateService;

    @Autowired
    ChallengeRepository taskRepository;

    @Autowired
    DayLifeRepository dayLifeRepository;

    Logger log = LoggerFactory.getLogger( ChallengeDeleteService.class );

    public boolean deleteTaskById(long id) {
        try {
            Optional<Challenge> t = taskRepository.findById(id);
            taskRepository.delete(t.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    //DELETE TASK FROM DAYLIFE
    public boolean deleteChallengeFromDayLife( DayLife daylife, Challenge challenge ){

        boolean removeFromChallenges = daylife.getChallenges().remove( challenge );
        boolean removeFromApprovedChallenges = daylife.getApprovedChallenges().remove( challenge );
        boolean removeDayLife = challenge.getDayLifes().remove( daylife );
        boolean removeApprovedDayLife = challenge.getApprovedDayLifes().remove( daylife );

        log.info(""+  removeFromChallenges +  removeFromApprovedChallenges + removeDayLife + removeApprovedDayLife );
        boolean result = ( removeFromChallenges || removeFromApprovedChallenges)
         && ( removeDayLife || removeApprovedDayLife );

        if ( result ){

            
            dayLifeRepository.save( daylife );
            deleteOrphanDayLife( daylife );
        }  
        return result;
    }

    public boolean deleteChallengeFromAllDayLife( Challenge challenge ){

        List < DayLife > dayLifes = challenge.getDayLifes().stream()
                                .collect(Collectors.toList());
        
        List < DayLife > approvedDayLifes = challenge.getApprovedDayLifes().stream()
                                .collect(Collectors.toList());

        boolean deletedDayLifes = dayLifes.stream()
            .allMatch( dayLife -> deleteChallengeFromDayLife( dayLife, challenge));


        boolean deletedApprovedDayLifes = approvedDayLifes.stream()
            .allMatch( dayLife -> deleteChallengeFromDayLife( dayLife, challenge));

                                 
        ArrayList < DayLife > orphanDayLifes = new ArrayList<>();

        //Colect orphan daylifes
        dayLifes.stream().filter( dayLife -> isOrphanDayLife( dayLife ) )
            .forEach( dayLife -> orphanDayLifes.add(dayLife));
        
        approvedDayLifes.stream().filter( dayLife -> isOrphanDayLife( dayLife ) )
            .forEach( dayLife -> orphanDayLifes.add( dayLife ));

        orphanDayLifes.stream().forEach( dayLife -> deleteOrphanDayLife( dayLife ) );
        

        boolean result = deletedDayLifes && deletedApprovedDayLifes;

        if ( result ){

            deleteTaskById( challenge.getId() );  
        }
        return result;
        
    }

    public boolean deleteTaskFromFutureDayLifes( Challenge challenge ){

        LocalDate today = LocalDate.now();
        System.out.println("todayś date: " + today.toString() );

        ArrayList < DayLife > dayLifes = (ArrayList<DayLife>) challenge.getDayLifes().stream()
                                .filter( dl -> dl.getDate().equals( today ) || dl.getDate().isAfter( today ) )
                                .collect(Collectors.toList());
        
        ArrayList < DayLife > approvedDayLifes = (ArrayList<DayLife>) challenge.getApprovedDayLifes().stream()
                                .filter( dl -> dl.getDate().equals(today) || dl.getDate().isAfter(today) )
                                .collect(Collectors.toList());

        boolean deletedDayLifes = dayLifes.stream()
            .allMatch( dayLife -> deleteChallengeFromDayLife( dayLife, challenge ) );


        boolean deletedApprovedDayLifes = approvedDayLifes.stream()
            .allMatch( dayLife -> deleteChallengeFromDayLife( dayLife, challenge ));

        ArrayList < DayLife > orphanDayLifes = new ArrayList<>();

        //Colect orphan daylifes
        dayLifes.stream().filter( dayLife -> isOrphanDayLife( dayLife ) )
            .forEach( dayLife -> orphanDayLifes.add( dayLife ));
        
        approvedDayLifes.stream().filter( dayLife -> isOrphanDayLife( dayLife ) )
            .forEach( dayLife -> orphanDayLifes.add( dayLife ));

        orphanDayLifes.stream().forEach( dayLife -> deleteOrphanDayLife( dayLife ) );
        

        return deletedDayLifes && deletedApprovedDayLifes;
    }

 
    public boolean deleteOrphanDayLife( DayLife dayLife ){
        return isOrphanDayLife( dayLife ) && dayLifeCreateUpdateService.deleteDayLifeById( dayLife.getId() );
    }

  
    public boolean isOrphanDayLife( DayLife dayLife ){
        return dayLife.getChallenges().size() == 0 && dayLife.getApprovedChallenges().size() == 0;
    }

   
}
