package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Voter;

public interface VoterRepository extends JpaRepository<Voter, Long>{

	
}
