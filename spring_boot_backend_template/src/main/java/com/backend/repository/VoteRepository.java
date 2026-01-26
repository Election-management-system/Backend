package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entities.Votes;

//public interface VoteRepository extends JpaRepository<Votes, Long> {
//
//    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
//}

public interface VoteRepository extends JpaRepository<Votes, Long> {

	@Query(
		    value = """
		            SELECT EXISTS (
		                SELECT 1
		                FROM votes
		                WHERE voter_id = :voterId
		                  AND election_id = :electionId
		            )
		            """,
		    nativeQuery = true
		)
		Long existsVoteByVoterAndElection(
		        @Param("voterId") Long voterId,
		        @Param("electionId") Long electionId
		);

	

	//List<Object[]> countVotesByElection(Long electionId);
	
	

	    @Query(
	        value = """
	                SELECT 
	                    v.candidate_id,
	                    v.election_id,
	                    COUNT(*) AS total_votes
	                FROM votes v
	                WHERE v.election_id = :electionId
	                GROUP BY v.candidate_id, v.election_id
	                """,
	        nativeQuery = true
	    )
	    List<Object[]> countVotesByElection(
	            @Param("electionId") Long electionId
	    );
	


}


