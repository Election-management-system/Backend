package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
