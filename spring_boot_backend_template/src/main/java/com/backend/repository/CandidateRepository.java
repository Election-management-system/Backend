package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.dtos.CandidateResponseDTO;
import com.backend.entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

//	boolean existsByVoter_Id(Long voterId);
//	boolean existsByVoterDetails_IdAndMyElection_Id(Long voterId, Long electionId);
	
	

	    @Query(
	        value = """
	                SELECT
	                  CASE
	                    WHEN COUNT(*) > 0 THEN TRUE
	                    ELSE FALSE
	                  END
	                FROM candidates
	                WHERE voter_id = :voterId
	                  AND election_id = :electionId
	                """,
	        nativeQuery = true
	    )
	    Long existsByVoterDetails_IdAndMyElection_Id(
	            @Param("voterId") Long voterId,
	            @Param("electionId") Long electionId
	    );
	    
	    @Query(
	            value = """
	                    SELECT 
	                      c.candidate_id,
	                      c.party_name,
	                      c.manifesto,
	                      v.voter_id,
	                      CONCAT(v.first_name, ' ', v.last_name) AS voter_name,
	                      v.email,
	                      e.election_id,
	                      e.election_name
	                    FROM candidates c
	                    JOIN voters v ON c.voter_id = v.voter_id
	                    JOIN elections e ON c.election_id = e.election_id
	                    WHERE c.is_approved = 0
	                    """,
	            nativeQuery = true
	        )
	        List<Object[]> findPendingCandidatesNative();
	}

