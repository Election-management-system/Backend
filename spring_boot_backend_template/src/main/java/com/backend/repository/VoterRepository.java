package com.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.entities.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long> {

	// List<Voter> findByIsApprovedFalse();

	@Query(value = """
			SELECT *
			FROM voters
			WHERE is_approved = 0
			""", nativeQuery = true)
	List<Voter> findAllPendingVoters();

	Optional<Voter> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByAadharCardNo(String aadharCardNo);
}
