package com.KarpeDiemHabits.TasksServer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;

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
