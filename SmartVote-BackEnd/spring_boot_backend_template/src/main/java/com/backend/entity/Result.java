package com.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "result")
@Getter
@Setter
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    private Election election;

    @ManyToOne
    private Candidate candidate;

    private String postName;
    private Long totalVotes;
    private boolean isWinner;
}

