package com.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.ElectionResultResponseDTO;
import com.backend.dtos.WinnerResponseDTO;
import com.backend.entities.Candidate;
import com.backend.entities.Election;
import com.backend.entities.ElectionResult;
import com.backend.repository.ElectionRepository;
import com.backend.repository.ElectionResultRepository;
import com.backend.repository.VoteRepository;

@Service
@Transactional
public class ResultServiceImpl implements ResultService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private ElectionResultRepository resultRepository;

    @Override
    public String declareElectionResults(Long electionId) {

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new RuntimeException("Election not found"));

        // 🔒 must be closed
        if (election.isIsactive()) {
            throw new RuntimeException(
                    "Election is still active. Close it first.");
        }

        // 🔒 results can be declared ONLY ONCE
        if (resultRepository.existsByElection_Id(electionId)) {
            throw new RuntimeException(
                    "Results already declared for this election");
        }

        // 🔢 count votes
        List<Object[]> counts =
                voteRepository.countVotesByElection(electionId);

        for (Object[] row : counts) {
            ElectionResult result = new ElectionResult();

            Candidate candidate =
                    new Candidate();
            candidate.setId(((Number) row[0]).longValue());

            result.setElection(election);
            result.setCandidate(candidate);
            result.setTotalVotes(
                    ((Number) row[4]).longValue());
            result.setLastUpdated(LocalDateTime.now());

            resultRepository.save(result);
        }

        return "Election results declared successfully";
    }

	@Override
	public List<ElectionResultResponseDTO> getStoredResults(Long electionId) {
		// TODO Auto-generated method stub
		return resultRepository.findByElection_Id(electionId)
	            .stream()
	            .map(r -> {
	                ElectionResultResponseDTO dto =
	                        new ElectionResultResponseDTO();

	                dto.setCandidateId(
	                        r.getCandidate().getId());
	                dto.setPartyName(
	                        r.getCandidate().getPartyName());
	                dto.setTotalVotes(
	                        r.getTotalVotes());

	                return dto;
	            })
	            .toList();
	}

	 @Override
	    public WinnerResponseDTO declareWinner(Long electionId) {

	        Election election = electionRepository.findById(electionId)
	                .orElseThrow(() ->
	                        new RuntimeException("Election not found"));

	        // 🔒 must be closed
	        if (election.isIsactive()) {
	            throw new RuntimeException(
	                    "Election is still active. Close it before declaring winner");
	        }

	        // 🔒 results must exist
	        if (!resultRepository.existsByElection_Id(electionId)) {
	            throw new RuntimeException(
	                    "Results not declared yet for this election");
	        }

	        ElectionResult winner =
	                resultRepository.findWinnerByElectionId(electionId);

	        if (winner == null) {
	            throw new RuntimeException(
	                    "No votes found for this election");
	        }

	        WinnerResponseDTO dto = new WinnerResponseDTO();
	        dto.setElectionId(election.getId());
	        dto.setElectionName(election.getElectionName());

	        dto.setCandidateId(winner.getCandidate().getId());
	        dto.setPartyName(winner.getCandidate().getPartyName());
	        dto.setTotalVotes(winner.getTotalVotes());

	        return dto;
	    }


}

