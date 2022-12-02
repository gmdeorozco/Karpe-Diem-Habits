package com.KarpeDiemHabits.ChallengesServer.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Builder
@Table ( name = "challenges" )
public class Challenge extends Activity{

    @Id 
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false, unique = true )
    private String name;

    @Column
    private String instructions;

    @Column( nullable = false )
    private String goal;

    @Column( nullable = false )
    private LocalDate initialDate;

    @Column( nullable = false )
    private LocalDate endDate;

    @Column
    private LocalTime initialTime;

    @Column
    private LocalTime endTime;

    @Column
    private boolean monday = false;

    @Column
    private boolean tuesday = false;

    @Column
    private boolean wednesday = false;

    @Column
    private boolean thursday = false;

    @Column
    private boolean friday = false;

    @Column
    private boolean saturday = false;

    @Column
    private boolean sunday = false;
 
    @JsonIgnore
    @ManyToMany
    (mappedBy = "approvedChallenges")
    private List<DayLife> ApprovedDayLifes = new ArrayList<>();

     @JsonIgnore
     @ManyToMany
     (mappedBy = "challenges")
     private List<DayLife> dayLifes = new ArrayList<>();

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


