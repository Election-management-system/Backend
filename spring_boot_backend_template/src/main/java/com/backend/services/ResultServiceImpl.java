package com.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.ElectionResultResponseDTO;
import com.backend.dtos.WinnerResponseDTO;
import com.backend.entities.Candidate;
import com.backend.entities.Election;
import com.backend.entities.ElectionResult;
import com.backend.exception.BadRequestException;
import com.backend.exception.BusinessRuleException;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.ElectionRepository;
import com.backend.repository.ElectionResultRepository;
import com.backend.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;
    private final ElectionResultRepository resultRepository;

    @Override
    public String declareElectionResults(Long electionId) {

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Election not found with ID: " + electionId)
                );

        // 🔒 Election must be closed
        if (election.isIsactive()) {
            throw new BusinessRuleException(
                    "Election is still active. Close it before declaring results");
        }

        // 🔒 Results can be declared only once
        if (resultRepository.existsByElection_Id(electionId)) {
            throw new BadRequestException(
                    "Results already declared for this election");
        }

        // 🔢 Count votes
        List<Object[]> counts =
                voteRepository.countVotesByElection(electionId);

        if (counts.isEmpty()) {
            throw new BusinessRuleException(
                    "No votes found for this election");
        }

        for (Object[] row : counts) {

            Candidate candidate = new Candidate();
            candidate.setId(((Number) row[0]).longValue());

            ElectionResult result = new ElectionResult();
            result.setElection(election);
            result.setCandidate(candidate);
            result.setTotalVotes(((Number) row[4]).longValue());
            result.setLastUpdated(LocalDateTime.now());

            resultRepository.save(result);
        }

        return "Election results declared successfully";
    }

    @Override
    public List<ElectionResultResponseDTO> getStoredResults(Long electionId) {

        if (!electionRepository.existsById(electionId)) {
            throw new ResourceNotFoundException(
                    "Election not found with ID: " + electionId);
        }

        return resultRepository.findByElection_Id(electionId)
                .stream()
                .map(r -> {
                    ElectionResultResponseDTO dto =
                            new ElectionResultResponseDTO();

                    dto.setCandidateId(r.getCandidate().getId());
                    dto.setPartyName(r.getCandidate().getPartyName());
                    dto.setTotalVotes(r.getTotalVotes());

                    return dto;
                })
                .toList();
    }

    @Override
    public WinnerResponseDTO declareWinner(Long electionId) {

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Election not found with ID: " + electionId)
                );

        // 🔒 Election must be closed
        if (election.isIsactive()) {
            throw new BusinessRuleException(
                    "Election is still active. Close it before declaring winner");
        }

        // 🔒 Results must exist
        if (!resultRepository.existsByElection_Id(electionId)) {
            throw new BadRequestException(
                    "Results not declared yet for this election");
        }

        ElectionResult winner =
                resultRepository.findWinnerByElectionId(electionId);

        if (winner == null) {
            throw new BusinessRuleException(
                    "No votes found to declare a winner");
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
