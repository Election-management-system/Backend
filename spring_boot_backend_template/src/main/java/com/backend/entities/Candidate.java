package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "candidates",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"voter_id", "election_id"})
    }
)
@AttributeOverride(name = "id", column = @Column(name = "candidate_id"))
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"voterDetails", "myElection"})
public class Candidate extends BaseEntity {

    @Column(name = "party_name", length = 50, nullable = false)
    private String partyName;

    @Lob
    @Column(name = "party_logo")
    private byte[] partyLogo;

    @Column(name = "manifesto", length = 500)
    private String manifesto;

    // Many candidates over time can belong to the same voter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private Voter voterDetails;

    // Many candidates belong to one election
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election myElection;

    @Column(name = "is_approved", nullable = false)
    private boolean approved = false;
}
