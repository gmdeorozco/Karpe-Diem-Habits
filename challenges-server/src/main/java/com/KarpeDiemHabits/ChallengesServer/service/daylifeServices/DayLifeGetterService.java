package com.KarpeDiemHabits.ChallengesServer.service.daylifeServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.ChallengesServer.entities.DayLife;
import com.KarpeDiemHabits.ChallengesServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;

@Service
public class DayLifeGetterService {


    @Autowired
    ChallengeRepository taskRepository;

    @Autowired
    DayLifeRepository dayLifeRepository;

  
    
    public ArrayList<DayLife> getAllDayLife() {
        return (ArrayList<DayLife>) dayLifeRepository.findAll();
    }

    
    public Optional<DayLife> getDayLifeById(long id) {
        return dayLifeRepository.findById(id);
    }

    public DayLife getDayLifeByDate(LocalDate date) {
        ArrayList<DayLife> allDayLifes = (ArrayList<DayLife>) dayLifeRepository.findAll();

        ArrayList<DayLife> dl = (ArrayList<DayLife>) allDayLifes.stream()
            .filter( daylife -> daylife.getDate().equals(date))
            .collect(Collectors.toList());

        return dl.size() > 0 ? dl.get(0) : null;
    }

  

    
}
