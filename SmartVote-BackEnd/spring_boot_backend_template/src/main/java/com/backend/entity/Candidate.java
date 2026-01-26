package com.backend.entity;

import com.backend.entity.enums.NominationStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "candidate")
@Getter
@Setter
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    private String postName;
    private String manifestoSummary;

    @Enumerated(EnumType.STRING)
    private NominationStatus nominationStatus = NominationStatus.PENDING;
}
