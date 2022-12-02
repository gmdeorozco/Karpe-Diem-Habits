package com.KarpeDiemHabits.TasksServer.service.taskServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KarpeDiemHabits.TasksServer.entities.DayLife;
import com.KarpeDiemHabits.TasksServer.entities.Task;
import com.KarpeDiemHabits.TasksServer.repository.TaskRepository;

@Service
public class TaskGetterService {

    @Autowired
    TaskRepository taskRepository;
    
    public ArrayList<Task> getAllTask() {
        return (ArrayList<Task>) taskRepository.findAll();
    }

    public Optional<Task> getTaskById(long id) {
        return taskRepository.findById(id);
    }

    public ArrayList<Task> getTasksByDate( LocalDate date ) {
        ArrayList< Task > allTasks = ( ArrayList< Task > ) taskRepository.findAll();

        ArrayList< Task > tasksWithDate = ( ArrayList< Task > ) allTasks.stream()
            .filter( task -> taskContainsDate( task, date ) )
            .collect(Collectors.toList());

        return tasksWithDate;
    }

    public boolean taskContainsDate( Task task, LocalDate date ){
        LocalDate taskInitialDate = task.getInitialDate();
        LocalDate taskEndDate = task.getEndDate();

        return ( taskInitialDate.equals(date) 
            || taskEndDate.equals(date) 
            || ( taskInitialDate.isBefore( date ) && taskEndDate.isAfter( date ) ));
    }

    public boolean isTaskWithinDayLife( DayLife dayLife, Long taskId ){

        boolean isOnTasks = dayLife.getTasks().stream()
        .anyMatch( task -> task.getId() == taskId);

        boolean isOnApprovedTasks = dayLife.getApprovedTasks().stream()
        .anyMatch( task -> task.getId() == taskId );

        return isOnTasks || isOnApprovedTasks;
            
    }

}
