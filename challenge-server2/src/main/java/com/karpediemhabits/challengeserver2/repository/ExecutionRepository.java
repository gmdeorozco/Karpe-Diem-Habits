package com.karpediemhabits.challengeserver2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.karpediemhabits.challengeserver2.entities.Execution;

public interface ExecutionRepository extends CrudRepository<Execution, Long>{
    public List<Execution> findByDate(LocalDate date);
}
