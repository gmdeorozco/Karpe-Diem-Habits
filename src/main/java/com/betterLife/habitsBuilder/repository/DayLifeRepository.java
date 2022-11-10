package com.betterLife.habitsBuilder.repository;

import org.springframework.data.repository.CrudRepository;

import com.betterLife.habitsBuilder.model.DayLife;

public interface DayLifeRepository extends CrudRepository < DayLife , Long > {
    
}
