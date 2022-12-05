package com.karpediemhabits.challengeserver2.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long challengeId;

    @Column( unique = true )
    private String challengeName;

    private String challengeGoal;
    private String challengeInstructions;

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    private LocalDate initialDate;
    private LocalDate endDate;

    private LocalTime time;

   
    public boolean dayOfWeekIsActive( java.time.DayOfWeek dayOfWeek ){
        return switch ( dayOfWeek ) {
            case SUNDAY -> this.sunday;
            case MONDAY -> this.monday;
            case TUESDAY -> this.tuesday;
            case WEDNESDAY -> this.wednesday;
            case THURSDAY -> this.thursday;
            case FRIDAY -> this.friday;
            case SATURDAY -> this.saturday;
        };
}
}