package com.backend.entities;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Elections")
@AttributeOverride(name = "id", column = @Column(name = "election_id"))
//Lombok annotations
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)

public class Election extends BaseEntity {

	@Column(name = "election_name", length = 100 , nullable = false)
	private String electionName;
	
	@Column(name = "election_post", length = 50 , nullable = false)
	private String electionPost;
	
	@Column(name = "election_date", nullable = false)
	private LocalDate electionDate;
	
	@Column(name = "nomination_start", nullable = false)
	private LocalDate nominationStartDate;
	
	@Column(name = "nomination_end", nullable = false)
	private LocalDate nominationEndDate;
	
	@Column(name = "campaign_end", nullable = false)
	private LocalDate campaignEndDate;
	
	@Column(name = "election_norms",length = 500, nullable = false)
	 private String electionNorms;
;
	
	@Column(name = "is_active")
	private boolean isactive;

	public Election(String electionName, String electionPost, LocalDate electionDate, LocalDate nominationStartDate,
			LocalDate nominationEndDate, LocalDate campaignEndDate, String elctionNorms) {
		super();
		this.electionName = electionName;
		this.electionPost = electionPost;
		this.electionDate = electionDate;
		this.nominationStartDate = nominationStartDate;
		this.nominationEndDate = nominationEndDate;
		this.campaignEndDate = campaignEndDate;
		this.electionNorms = elctionNorms;
	}
	
	
}
