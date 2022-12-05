package com.karpediemhabits.challengeserver2;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.karpediemhabits.challengeserver2.entities.Challenge;
import com.karpediemhabits.challengeserver2.service.ChallengeCreateUpdateService;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger( LoadDatabase.class );

    @Autowired
    ChallengeCreateUpdateService challengeCreateUpdateService;

    @Bean
    CommandLineRunner iniDatabase(){

        return args -> {
            Challenge challenge = Challenge.builder()
                .challengeGoal("do it everyday")
                .challengeName("Run")
                .friday(true)
                .initialDate(LocalDate.parse("2022-12-02"))
                .endDate(LocalDate.parse("2022-12-23"))
                .build();
            
                challengeCreateUpdateService.createChallenge( challenge );

            Challenge challenge2 = Challenge.builder()
                .challengeGoal("do it everyday")
                .challengeName("Pray")
                .friday(true)
                .monday(true)
                .tuesday(true)
                .wednesday(true)
                .thursday(true)
                .saturday(true)
                .sunday(true)
                .initialDate(LocalDate.parse("2022-12-02"))
                .endDate(LocalDate.parse("2022-12-23"))
                .build();
            
                challengeCreateUpdateService.createChallenge( challenge2 );

                
        };
    }
}
