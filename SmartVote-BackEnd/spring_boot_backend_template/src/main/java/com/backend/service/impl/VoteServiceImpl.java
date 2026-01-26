package com.backend.service.impl;

import com.backend.exception.BadRequestException;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dto.request.VoteRequest;
import com.backend.entity.Candidate;
import com.backend.entity.Election;
import com.backend.entity.Vote;
import com.backend.entity.Voter;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.CandidateRepository;
import com.backend.repository.ElectionRepository;
import com.backend.repository.VoteRepository;
import com.backend.repository.VoterRepository;
import com.backend.service.VoteService;

@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepo;
    private final VoterRepository voterRepo;
    private final ElectionRepository electionRepo;
    private final CandidateRepository candidateRepo;
    
    public VoteServiceImpl(
            VoteRepository voteRepo,
            VoterRepository voterRepo,
            ElectionRepository electionRepo,
            CandidateRepository candidateRepo) {

        this.voteRepo = voteRepo;
        this.voterRepo = voterRepo;
        this.electionRepo = electionRepo;
        this.candidateRepo = candidateRepo;
    }

    public void castVote(Long voterId, VoteRequest request) {

        Voter voter = voterRepo.findById(voterId)
            .orElseThrow(() -> new ResourceNotFoundException("Voter not found"));

        if (!voter.isEligible())
            throw new BadRequestException("Voter not verified");

        Election election = electionRepo.findById(request.getElectionId())
            .orElseThrow(() -> new ResourceNotFoundException("Election not found"));

        if (!election.isActive())
            throw new BadRequestException("Election inactive");

        if (voteRepo.existsByElectionAndVoterAndPostName(
                election, voter, request.getPostName()))
            throw new BadRequestException("Already voted for this post");

        Candidate candidate = candidateRepo.findById(request.getCandidateId())
            .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

        Vote vote = new Vote();
        vote.setElection(election);
        vote.setVoter(voter);
        vote.setCandidate(candidate);
        vote.setPostName(request.getPostName());

        voteRepo.save(vote);
    }
}
