package com.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "election")
@Getter
@Setter
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electionId;

    private String electionName;
    private LocalDate electionDate;
    private LocalDate nominationStartDate;
    private LocalDate nominationEndDate;
    private LocalDate campaignEndDate;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "created_by_admin_id")
    private Admin createdBy;
}
