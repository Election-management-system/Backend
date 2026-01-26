package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.entities.ElectionResult;

public interface ElectionResultRepository extends JpaRepository<ElectionResult, Long> {

  boolean existsByElection_Id(Long electionId);

  List<ElectionResult> findByElection_Id(Long electionId);
  
  @Query(
	        value = """
	                SELECT *
	                FROM election_results
	                WHERE election_id = :electionId
	                ORDER BY total_votes DESC
	                LIMIT 1
	                """,
	        nativeQuery = true
	    )
	    ElectionResult findWinnerByElectionId(
	            @Param("electionId") Long electionId
	    );
}

