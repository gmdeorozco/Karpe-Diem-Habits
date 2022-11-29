package com.KarpeDiemHabits.TasksServer.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;

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

    public ArrayList<DayLife> createDayLifesByInterval(Task task, LocalDate initialDate, LocalDate endDate);

    public void recalculateDayLifes(Task task) ;

    public void recalculateFutureDayLifes(Task task) ;
}