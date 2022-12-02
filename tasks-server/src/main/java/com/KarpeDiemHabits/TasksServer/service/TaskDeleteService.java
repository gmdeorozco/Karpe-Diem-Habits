package com.KarpeDiemHabits.TasksServer.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;

@Service
public class TaskDeleteService {
    
    @Autowired
    HabitsBuilderService habitsBuilderService;

    Logger log = LoggerFactory.getLogger( TaskDeleteService.class );

    //DELETE TASK FROM DAYLIFE
   
    public boolean deleteTaskFromDayLife( DayLife daylife, Task task ){

        boolean removeFromTasks = daylife.getTasks().remove( task );
        boolean removeFromApprovedTasks = daylife.getApprovedTasks().remove( task );
        boolean removeDayLife = task.getDayLifes().remove( daylife );
        boolean removeApprovedDayLife = task.getApprovedDayLifes().remove( daylife );

        log.info(""+  removeFromTasks +  removeFromApprovedTasks + removeDayLife + removeApprovedDayLife );
        boolean result = ( removeFromTasks || removeFromApprovedTasks)
         && ( removeDayLife || removeApprovedDayLife );

        if ( result ){

            log.warn("Deleted Task " + 
                task.getName() + 
                " from Daylife: " + 
                daylife.getDate());
            log.warn( "Task id " + task.getId());
            log.warn( "Daylife id " + daylife.getId());

            habitsBuilderService.saveDayLife( daylife );
            //habitsBuilderService.saveTask(task);
            //deleteOrphanDayLife( daylife );
        }  
        return result;
    }

    public boolean deleteTaskFromAllDayLife( Task task ){

        List < DayLife > dayLifes = task.getDayLifes().stream()
                                .collect(Collectors.toList());
        
        List < DayLife > approvedDayLifes = task.getApprovedDayLifes().stream()
                                .collect(Collectors.toList());

        boolean deletedDayLifes = dayLifes.stream()
            .allMatch( dayLife -> deleteTaskFromDayLife( dayLife, task));


        boolean deletedApprovedDayLifes = approvedDayLifes.stream()
            .allMatch( dayLife -> deleteTaskFromDayLife( dayLife, task));

                                 
        ArrayList < DayLife > orphanDayLifes = new ArrayList<>();

        //Colect orphan daylifes
        dayLifes.stream().filter( dayLife -> isOrphanDayLife( dayLife ) )
            .forEach( dayLife -> orphanDayLifes.add(dayLife));
        
        approvedDayLifes.stream().filter( dayLife -> isOrphanDayLife( dayLife ) )
            .forEach( dayLife -> orphanDayLifes.add( dayLife ));

        orphanDayLifes.stream().forEach( dayLife -> deleteOrphanDayLife( dayLife ) );
        

        boolean result = deletedDayLifes && deletedApprovedDayLifes;

        if ( result ){

            //habitsBuilderService.saveTask( task );
            habitsBuilderService.deleteTaskById( task.getId() );  
        }
        return result;
        /**/ // return true;
    }

    public boolean deleteTaskFromFutureDayLifes( Task task ){

        LocalDate today = LocalDate.now();
        System.out.println("todayś date: " + today.toString() );

        ArrayList < DayLife > dayLifes = (ArrayList<DayLife>) task.getDayLifes().stream()
                                .filter( dl -> dl.getDate().equals( today ) || dl.getDate().isAfter( today ) )
                                .collect(Collectors.toList());
        
        ArrayList < DayLife > approvedDayLifes = (ArrayList<DayLife>) task.getApprovedDayLifes().stream()
                                .filter( dl -> dl.getDate().equals(today) || dl.getDate().isAfter(today) )
                                .collect(Collectors.toList());

        boolean deletedDayLifes = dayLifes.stream()
            .allMatch( dayLife -> deleteTaskFromDayLife( dayLife, task));


        boolean deletedApprovedDayLifes = approvedDayLifes.stream()
            .allMatch( dayLife -> deleteTaskFromDayLife( dayLife, task));

        ArrayList < DayLife > orphanDayLifes = new ArrayList<>();

        //Colect orphan daylifes
        dayLifes.stream().filter( dayLife -> isOrphanDayLife(dayLife) )
            .forEach( dayLife -> orphanDayLifes.add(dayLife));
        
        approvedDayLifes.stream().filter( dayLife -> isOrphanDayLife(dayLife) )
            .forEach( dayLife -> orphanDayLifes.add(dayLife));

        orphanDayLifes.stream().forEach( dayLife -> deleteOrphanDayLife(dayLife) );
        

        return deletedDayLifes && deletedApprovedDayLifes;
    }

 
    public boolean deleteOrphanDayLife( DayLife dayLife ){
        return isOrphanDayLife( dayLife ) && habitsBuilderService.deleteDayLifeById( dayLife.getId() );
    }

  
    public boolean isOrphanDayLife( DayLife dayLife ){
        return dayLife.getTasks().size() == 0 && dayLife.getApprovedTasks().size() == 0;
    }

   
    public Task updateTask(Task newTask) {
        return habitsBuilderService.getTaskById( newTask.getId() )
            .map(  tsk  ->  {
                deleteTaskFromFutureDayLifes( tsk );
                habitsBuilderService.recalculateFutureDayLifes( habitsBuilderService.saveTask( newTask ) );
                return newTask;
            }).orElse(  null );
    }
}
