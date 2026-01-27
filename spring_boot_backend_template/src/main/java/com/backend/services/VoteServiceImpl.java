package com.backend.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.VoteRequestDTO;
import com.backend.entities.Candidate;
import com.backend.entities.Election;
import com.backend.entities.Voter;
import com.backend.entities.Votes;
import com.backend.exception.BusinessRuleException;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.CandidateRepository;
import com.backend.repository.ElectionRepository;
import com.backend.repository.VoteRepository;
import com.backend.repository.VoterRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final ElectionService electionService;
    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    @Override
    public String castVote(VoteRequestDTO dto) {

      

        // 2️⃣ Fetch voter
        Voter voter = voterRepository.findById(dto.getVoterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter not found with ID: " + dto.getVoterId())
                );

        // 3️⃣ Check voter approval
        if (!voter.isApproved()) {
            throw new BusinessRuleException(
                    "Voter is not approved to vote");
        }

        // 4️⃣ Fetch election
        Election election = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Election not found with ID: " + dto.getElectionId())
                );

        // 🔒 FAIL-SAFE AUTO CLOSE
        electionService.autoCloseIfExpired(election);

        // 5️⃣ Check election status
        if (!election.isIsactive()) {
            throw new BusinessRuleException(
                    "Election is not active");
        }

        // 6️⃣ Voting allowed only on election day
        if (!election.getElectionDate().equals(LocalDate.now())) {
            throw new BusinessRuleException(
                    "Voting is not allowed today");
        }

        // 7️⃣ Fetch candidate
        Candidate candidate = candidateRepository.findById(dto.getCandidateId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate not found with ID: " + dto.getCandidateId())
                );

        // 8️⃣ Check candidate approval
        if (!candidate.isApproved()) {
            throw new BusinessRuleException(
                    "Candidate is not approved");
        }

        // 9️⃣ Ensure candidate belongs to same election
        if (!candidate.getMyElection().getId().equals(election.getId())) {
            throw new BusinessRuleException(
                    "Candidate does not belong to this election");
        }
        
        // 1️⃣ Check if voter already voted in this election
        Long exists = voteRepository.existsVoteByVoterAndElection(
                dto.getVoterId(), dto.getElectionId());

        if (exists != null && exists > 0) {
            throw new BusinessRuleException(
                    "Voter has already voted in this election");
        }

        // 🔟 Create vote entry
        Votes vote = new Votes();
        vote.setVoter(voter);
        vote.setMyCandidate(candidate);
        vote.setElection(election);

        voteRepository.save(vote);

        // 🔒 Optional global voted flag
//        voter.setVoted(true);
//        voterRepository.save(voter);

        return "Vote cast successfully";
    }
}
