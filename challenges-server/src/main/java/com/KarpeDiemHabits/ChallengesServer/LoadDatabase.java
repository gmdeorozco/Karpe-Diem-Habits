package com.KarpeDiemHabits.ChallengesServer;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.KarpeDiemHabits.ChallengesServer.repository.DayLifeRepository;
import com.KarpeDiemHabits.ChallengesServer.entities.Challenge;
import com.KarpeDiemHabits.ChallengesServer.repository.ChallengeRepository;
import com.KarpeDiemHabits.ChallengesServer.service.daylifeServices.DayLifeGetterService;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger( LoadDatabase.class );

    @Autowired
    ChallengeRepository taskRepository;

    @Bean
    CommandLineRunner iniDatabase(){

        return args -> {
            Challenge task = Challenge.builder()
                .endDate(LocalDate.parse("2022-12-31"))
                .initialDate(LocalDate.parse("2022-12-01"))
                .monday(false)
                .tuesday(false)
                .wednesday(false)
                .thursday(true)
                .friday(true)
                .saturday(true)
                .sunday(true)
                .name("Walk for excersice")
                .instructions("10K steps a day")
                .goal("Do it for 6 months from friday to Sunday")
                .build();
            
                taskRepository.save( task );
        };
    }
}
