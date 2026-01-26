package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.Candidate;
import com.backend.entity.Election;
import com.backend.entity.Vote;
import com.backend.entity.Voter;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByElectionAndVoterAndPostName(
        Election election, Voter voter, String postName);

    Long countByCandidate(Candidate candidate);
}
