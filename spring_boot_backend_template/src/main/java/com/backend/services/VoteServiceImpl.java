package com.backend.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.VoteRequestDTO;
import com.backend.entities.Candidate;
import com.backend.entities.Election;
import com.backend.entities.Voter;
import com.backend.entities.Votes;
import com.backend.repository.CandidateRepository;
import com.backend.repository.ElectionRepository;
import com.backend.repository.VoteRepository;
import com.backend.repository.VoterRepository;

@Service
@Transactional
public class VoteServiceImpl implements VoteService {
	
	@Autowired
	private ElectionService electionService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public String castVote(VoteRequestDTO dto) {

        // 1️⃣ Check if voter already voted in this election
    	Long exists = voteRepository.existsVoteByVoterAndElection(
    	        dto.getVoterId(), dto.getElectionId());

    	if (exists == 1L) 
    	    throw new RuntimeException("Voter has already voted in this election");

        // 2️⃣ Fetch voter
        Voter voter = voterRepository.findById(dto.getVoterId())
                .orElseThrow(() ->
                        new RuntimeException("Voter not found with ID: " + dto.getVoterId())
                );

        // 3️⃣ Check voter approval
        if (!voter.isApproved()) {
            throw new RuntimeException("Voter is not approved to vote");
        }

        // 4️⃣ Fetch election
        Election election = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() ->
                        new RuntimeException("Election not found with ID: " + dto.getElectionId())
                );
        
    
        // 🔒 FAIL-SAFE AUTO CLOSE
        electionService.autoCloseIfExpired(election);

       

        // 5️⃣ Check election status
        if (!election.isIsactive()) {
            throw new RuntimeException("Election is not active");
        }

       //  (Optional but recommended)
         if (!election.getElectionDate().equals(LocalDate.now())) {
             throw new RuntimeException("Voting is not allowed today");
         }

        // 6️⃣ Fetch candidate
        Candidate candidate = candidateRepository.findById(dto.getCandidateId())
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found with ID: " + dto.getCandidateId())      
                );
        
        if (!candidate.isApproved()) {
            throw new RuntimeException("Candidate is not approved");
        }

        // 7️⃣ Ensure candidate belongs to the same election
        if (!candidate.getMyElection().getId().equals(election.getId())) {
            throw new RuntimeException("Candidate does not belong to this election");
        }

        // 8️⃣ Create vote entry
        Votes vote = new Votes();
        vote.setVoter(voter);
        vote.setMyCandidate(candidate);
        vote.setElection(election);

        voteRepository.save(vote);

        // 9️⃣ (Optional global flag)
        voter.setVoted(true);
        voterRepository.save(voter);

        return "Vote cast successfully";
    }

}



