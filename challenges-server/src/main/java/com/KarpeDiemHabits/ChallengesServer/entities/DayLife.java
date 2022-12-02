package com.KarpeDiemHabits.ChallengesServer.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DayLife {

    @Id 
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( nullable = false, unique = true )
    private LocalDate date;

    @ManyToMany
    @JoinColumn(name = "approved_challenge_id")
    //@JsonIgnore
    private List<Challenge> approvedChallenges = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "challenge_id")
    //@JsonIgnore
    private List<Challenge> challenges = new ArrayList<>();

   

    public boolean deleteChallenge( Challenge challenge ){
        return getChallenges().remove(challenge) &&
        getApprovedChallenges().remove(challenge)&&
        challenge.getDayLifes().remove(this);
    }

    public String getDayOfWeek(){
        return getDate().getDayOfWeek().toString();
    }

}
