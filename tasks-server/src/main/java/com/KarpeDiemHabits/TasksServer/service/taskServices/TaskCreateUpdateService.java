package com.KarpeDiemHabits.TasksServer.service.taskServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.repository.TaskRepository;
import com.KarpeDiemHabits.TasksServer.service.daylifeServices.DayLifeCreateUpdateService;

@Service
public class TaskCreateUpdateService {
    
    @Autowired
    TaskGetterService taskGetterService;

    @Autowired
    TaskDeleteService taskDeleteService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    DayLifeCreateUpdateService dayLifeCreateUpdateService;

    public Task saveTask(Task t) {

        dayLifeCreateUpdateService.recalculateDayLifes(taskRepository.save(t));
        return t;
    }
    

    public Task updateTask(Task newTask) {
        return taskGetterService.getTaskById( newTask.getId() )
            .map(  tsk  ->  {
                taskDeleteService.deleteTaskFromFutureDayLifes( tsk );
                dayLifeCreateUpdateService.recalculateFutureDayLifes( taskRepository.save( newTask ) );
                return newTask;
            }).orElse(  null );
    }
    
}
