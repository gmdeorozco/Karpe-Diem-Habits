package com.karpediemhabits.executionssever;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.karpediemhabits.executionssever.entities.Execution;
import com.karpediemhabits.executionssever.repository.ExecutionRepository;
import com.karpediemhabits.executionssever.service.ExecutionService;



@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger( LoadDatabase.class );

    @Autowired
    ExecutionService executionService;

    @Autowired
    ExecutionRepository executionRepository;

    @Bean
    CommandLineRunner iniDatabase(){

        return args -> {
            Execution execution = Execution.builder()
                .challengeApproved(false)
                .challengeStarted(false)
                .date(LocalDate.parse("2022-12-02"))
                .time(LocalTime.now())
                .challengeId(1L)
                .build();
            
                executionRepository.save( execution );

            Execution execution2 = Execution.builder()
                .challengeApproved(false)
                .challengeStarted(false)
                .date(LocalDate.parse("2022-12-02"))
                .time(LocalTime.now())
                .build();
            
                executionRepository.save( execution2 );

                
        };
    }
}
