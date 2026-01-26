package com.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voterlist")
@Setter
@Getter
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voterId;

    private String studentOrMemberId;
    private String fullName;
    private String email;
    private String courseOrDepartment;

    private boolean isEligible = false;

    @OneToMany(mappedBy = "voter")
    @JsonIgnore
    private List<Vote> votes;
}
