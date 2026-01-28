package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "election_results",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"election_id", "candidate_id"})
    }
)
@AttributeOverride(name = "id", column = @Column(name = "result_id"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"candidate", "election"})
public class ElectionResult extends BaseEntity{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "total_votes", nullable = false)
    private Long totalVotes;

	

   
}


