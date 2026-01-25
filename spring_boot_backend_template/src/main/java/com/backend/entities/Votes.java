package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "votes")
@AttributeOverride(name = "id", column = @Column(name = "vote_id"))
//Lombok annotations
@NoArgsConstructor
@Getter
@Setter

@ToString(callSuper = true , exclude = {"myCandidate" , "myElection"})
public class Votes extends BaseEntity {
	
	    // vote 1-->1 Voter
		@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY) //mandatory
		@JoinColumn(name="voter_id",nullable = false)
		private Voter VoterDetails;
		
		// votes * ------> 1 candidate (many to one)
		@ManyToOne
		@JoinColumn(name="candidate_id",nullable = false)
		private Candidate myCandidate;
		
		
		// votes * ------> 1 election (many to one)
		@ManyToOne
		@JoinColumn(name="election_id",nullable = false)
		private Election myElection;

}
