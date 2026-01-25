package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "candidates")
@AttributeOverride(name = "id", column = @Column(name = "candidate_id"))
//Lombok annotations
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true , exclude = {"VoterDetails"})
public class Candidate extends  BaseEntity {
	
	@Column(name = "party_name", length = 50 , nullable = false)
	private String partyName;
	
	@Column(name = "party_logo", length = 200)
	private byte[] partyLogo;
	
	@Column(name = "manifesto", length = 500)
	private String manifesto;
	
	// Candidate 1-->1 Voter
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY) //mandatory
	@JoinColumn(name="voter_id",nullable = false)
	private Voter VoterDetails;
	
//	@Column(name = "total_votes")
//	private int totalVotes;
//	
//	@Column(name = "is_winner")
//	private boolean isWinner;

	
}
