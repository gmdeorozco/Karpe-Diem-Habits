package com.karpediemhabits.executionssever.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.karpediemhabits.executionssever.entities.Execution;

public interface ExecutionRepository extends CrudRepository<Execution, Long>{

    public List<Execution> findByDate(LocalDate date);

    @Query("SELECT c.challengeApproved, COUNT(c.challengeApproved) FROM Execution AS c GROUP BY c.challengeApproved ORDER BY c.challengeApproved DESC")
    List<Object[]> countApprove();
}