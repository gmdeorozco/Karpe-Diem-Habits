package com.betterLife.habitsBuilder.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betterLife.habitsBuilder.model.DayLife;

@Service
public class OrphanDayLifeService {

    @Autowired
    HabitsBuilderService habitsBuilderService;

    public OrphanDayLifeService(){

    }

    public boolean deleteOrphanDayLife( DayLife dayLife ){
        return isOrphanDayLife( dayLife ) && habitsBuilderService.deleteDayLifeById( dayLife.getId() );
    }

    public boolean isOrphanDayLife( DayLife dayLife ){
        return dayLife.getTasks().size() == 0 && dayLife.getApprovedTasks().size() == 0;
    }
}
