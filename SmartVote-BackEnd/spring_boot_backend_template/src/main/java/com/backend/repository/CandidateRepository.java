package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.Candidate;
import com.backend.entity.Election;
import com.backend.entity.enums.NominationStatus;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByElectionAndPostNameAndNominationStatus(
        Election election, String post, NominationStatus status);
}
