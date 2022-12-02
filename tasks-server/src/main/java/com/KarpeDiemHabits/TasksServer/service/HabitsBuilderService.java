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
    ArrayList<Task> getTasksByDate( LocalDate date)

    Optional <Task> getTaskById( long id );

    boolean deleteTaskById( long id );

    //DAYLIFE

    DayLife saveDayLife ( DayLife d );

    ArrayList<DayLife> getAllDayLife();

    Optional <DayLife> getDayLifeById( long id );

    boolean deleteDayLifeById( long id );

    DayLife getDayLifeByDate(LocalDate date) ;

    public ArrayList<DayLife> createDayLifesByInterval(Task task, LocalDate initialDate, LocalDate endDate);

    public void recalculateDayLifes(Task task) ;

    public boolean findTaskWithinDayLife( DayLife dayLife, Long taskId );

    public  DayLife approveTask( Long dayLifeId, Long taskId );

    public  DayLife failTask ( Long dayLifeId, Long taskId );

    public boolean taskContainsDate( Task task, LocalDate date );

    public void recalculateFutureDayLifes( Task task );

    
    
}
