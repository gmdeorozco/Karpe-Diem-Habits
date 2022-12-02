package com.KarpeDiemHabits.TasksServer.service.daylifeServices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.TasksServer.repository.TaskRepository;
import com.KarpeDiemHabits.TasksServer.service.taskServices.TaskGetterService;

@Service
public class DayLifeScoreService {

    @Autowired
    DayLifeRepository dayLifeRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskGetterService taskGetterService;



    public DayLifeScoreService(){}

    public double getScore( DayLife dayLife ){
        return ( 
            dayLife.getApprovedTasks().size() * 100 ) / ( dayLife.getApprovedTasks().size() 
                + dayLife.getTasks().size() );
    }

    public DayLife approveTask( Long dayLifeId, Long taskId ){
        Optional < DayLife > dayLife = dayLifeRepository.findById( dayLifeId );
        Optional < Task > task = taskRepository.findById( taskId );
        
        if ( dayLife.isPresent() && task.isPresent() &&
            taskGetterService.isTaskWithinDayLife( dayLife.get(), taskId ) &&
            dayLife.get().getTasks().remove( task.get() ) &&
            dayLife.get().getApprovedTasks().add( task.get() )){
                return dayLifeRepository.save(dayLife.get());
            }
        return null;
    }

   
    public DayLife failTask(Long dayLifeId, Long taskId) {
        Optional <DayLife> dayLife = dayLifeRepository.findById( dayLifeId );
        Optional <Task> task = taskRepository.findById( taskId );
        
        if( dayLife.isPresent() && task.isPresent() &&
            taskGetterService.isTaskWithinDayLife(dayLife.get(), taskId) &&
            dayLife.get().getApprovedTasks().remove(task.get()) &&
            dayLife.get().getTasks().add(task.get()) ){
           
                return dayLifeRepository.save(dayLife.get());
        } else{
            return null;
        }

    }
}
