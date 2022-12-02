package com.KarpeDiemHabits.ChallengesServer.entities;

import java.lang.annotation.Inherited;
import java.util.Optional;

@Data 
public class Activity {

    @Id 
    private Long id;

    private Optional< Challenge > challege;

    private boolean challengeCompleted;
    private boolean activityInitialized;
    private boolean activityFinished;
    

    
}
