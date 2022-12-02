package com.KarpeDiemHabits.ChallengesServer.service.daylifeServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;

@Service
public class DayLifeCreateUpdateService {


    @Autowired
    ChallengeRepository challengeRepository;

    @Autowired
    DayLifeRepository dayLifeRepository;

    @Autowired
    DayLifeGetterService dayLifeGetterService;
 
    public DayLife saveDayLife(DayLife d) {
        return dayLifeRepository.save(d);
    }
       
    public boolean deleteDayLifeById(long id) {
        try {
            Optional<DayLife> d = dayLifeRepository.findById(id);
            dayLifeRepository.delete(d.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public ArrayList<DayLife> createDayLifesByInterval(Challenge challenge, LocalDate initialDate, LocalDate endDate){
        
        ArrayList < DayLife > dayLifes = new ArrayList<>();
        
        for( LocalDate date = initialDate; 
                date.isBefore( endDate ) || date.isEqual( endDate ); 
                date = date.plusDays(1 ) ){

                    if ( !challenge.dayOfWeekIsActive(date.getDayOfWeek()) ) { continue; }
                    

                    DayLife existentDayLife = dayLifeGetterService.getDayLifeByDate(date);

                    if ( existentDayLife == null ){
                        DayLife dl = new DayLife();
                        dl.setDate(date);
                        dl.setChallenges(new ArrayList<>());
                        dl.getChallenges().add(challenge);
                        dl = dayLifeRepository.save( dl );
                        
                        dayLifes.add( dl );
                    }else{
                        
                        existentDayLife.getChallenges().add(challenge);
                        dayLifeRepository.save(existentDayLife);
                    }

                    


        }
        return dayLifes;
    }
    
    public void recalculateDayLifes(Challenge challenge) {
        ArrayList< DayLife > relevantDayLifes = 
         createDayLifesByInterval(challenge, challenge.getInitialDate(), challenge.getEndDate());
        challenge.setDayLifes( relevantDayLifes );
    }

    public void recalculateFutureDayLifes(Challenge challenge) {
        LocalDate today = LocalDate.now();
        ArrayList< DayLife > relevantDayLifes = 
        createDayLifesByInterval(challenge, today, challenge.getEndDate());
        challenge.setDayLifes( relevantDayLifes );
    }

   
    
}
