package com.KarpeDiemHabits.TasksServer.repository;

import org.springframework.data.repository.CrudRepository;

import com.KarpeDiemHabits.TasksServer.entities.Task;

public interface TaskRepository extends CrudRepository < Task, Long >{
    
}
