package com.betterLife.habitsBuilder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.betterLife.habitsBuilder.model.DayLife;
import com.betterLife.habitsBuilder.model.Task;

public interface HabitsBuilderService {
    
    //TASK

    Task saveTask( Task t );

    ArrayList<Task> getAllTask();

    Optional <Task> getTaskById( long id );

    boolean deleteTaskById( long id );

    //DAYLIFE

    DayLife saveDayLife ( DayLife d );

    ArrayList<DayLife> getAllDayLife();

    Optional <DayLife> getDayLifeById( long id );

    boolean deleteDayLifeById( long id );

    DayLife getDayLifeByDate(LocalDate date) ;

    ArrayList<Task> getTasksByDate( LocalDate date );
}
