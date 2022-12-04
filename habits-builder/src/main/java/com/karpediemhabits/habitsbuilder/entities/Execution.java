package com.karpediemhabits.habitsbuilder.entities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Execution {
     
    @Id
    @GeneratedValue
    private Long id;

    @Column( nullable = false )
    private LocalDate date;

    
    private LocalTime time;

    private boolean challengeApproved;
    private boolean challengeStarted;

    
    @ManyToOne
    private Challenge challenge;

    public DayOfWeek getDayOfWeek(){
        return date.getDayOfWeek();
    }

}
