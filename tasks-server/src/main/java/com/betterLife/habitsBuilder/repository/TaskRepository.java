package com.betterLife.habitsBuilder.repository;

import org.springframework.data.repository.CrudRepository;

import com.betterLife.habitsBuilder.model.Task;

public interface TaskRepository extends CrudRepository < Task, Long >{
    
}
