package com.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(
  name = "votes",
  uniqueConstraints = @UniqueConstraint(
    columnNames = {"election_id", "voter_id", "post_name"}
))
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    private Election election;

    @ManyToOne
    private Voter voter;

    @ManyToOne
    private Candidate candidate;

    private String postName;
    private LocalDateTime votedAt = LocalDateTime.now();
}
