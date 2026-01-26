package com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.CandidateRegisterDTO;
import com.backend.dtos.CandidateResponseDTO;
import com.backend.dtos.PendingCandidateResponseDTO;
import com.backend.entities.Candidate;
import com.backend.entities.Election;
import com.backend.entities.Voter;
import com.backend.repository.CandidateRepository;
import com.backend.repository.ElectionRepository;
import com.backend.repository.VoterRepository;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public String registerCandidate(CandidateRegisterDTO dto) {

        // 1️⃣ Fetch voter
        Voter voter = voterRepository.findById(dto.getVoterId())
                .orElseThrow(() ->
                        new RuntimeException("Voter not found")
                );

        if (!voter.isApproved()) {
            throw new RuntimeException("Voter is not approved");
        }
        
        // 3️⃣ Fetch election
        Election election = electionRepository.findById(dto.getElectionId())
                .orElseThrow(() ->
                        new RuntimeException("Election not found")
                );

        if (!election.isIsactive()) {
            throw new RuntimeException("Election is not active for nominations");
        }

        // 2️⃣ Ensure voter is not already a candidate
        Long exists = candidateRepository
                .existsByVoterDetails_IdAndMyElection_Id(voter.getId(), election.getId());

        if (exists != null && exists > 0) {
            throw new RuntimeException(
                "Voter is already registered as a candidate for this election"
            );
        }

        // 4️⃣ Create candidate
        Candidate candidate = new Candidate();
        candidate.setVoterDetails(voter);
        candidate.setMyElection(election);
        candidate.setPartyName(dto.getPartyName());
        candidate.setManifesto(dto.getManifesto());

        candidateRepository.save(candidate);

        return "Candidate registered successfully (Pending approval)";
    }

    
    
    @Override
    public List<CandidateResponseDTO> getAllCandidates() {

        return candidateRepository.findAll()
                .stream()
                .map(candidate -> {
                    CandidateResponseDTO dto = new CandidateResponseDTO();

                    dto.setCandidateId(candidate.getId());
                    dto.setPartyName(candidate.getPartyName());
                    dto.setManifesto(candidate.getManifesto());
                    dto.setApproved(candidate.isApproved());

                    dto.setVoterId(candidate.getVoterDetails().getId());
                    dto.setVoterName(
                            candidate.getVoterDetails().getFirstName() + " " +
                            candidate.getVoterDetails().getLastName()
                    );
                    dto.setVoterEmail(candidate.getVoterDetails().getEmail());

                    dto.setElectionId(candidate.getMyElection().getId());
                    dto.setElectionName(candidate.getMyElection().getElectionName());

                    return dto;
                })
                .toList();
    }



    @Override
    public String approveCandidate(Long candidateId) {

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Candidate not found with ID: " + candidateId)
                );

        // Optional safety check
        if (candidate.isApproved()) {
            return "Candidate is already approved";
        }

        candidate.setApproved(true);
        candidateRepository.save(candidate);

        return "Candidate approved successfully";
	}



    @Override
    public List<PendingCandidateResponseDTO> getPendingCandidates() {

        return candidateRepository.findPendingCandidatesNative()
                .stream()
                .map(row -> {
                    PendingCandidateResponseDTO dto =
                            new PendingCandidateResponseDTO();

                    dto.setCandidateId(((Number) row[0]).longValue());
                    dto.setPartyName((String) row[1]);
                    dto.setManifesto((String) row[2]);

                    dto.setVoterId(((Number) row[3]).longValue());
                    dto.setVoterName((String) row[4]);
                    dto.setVoterEmail((String) row[5]);

                    dto.setElectionId(((Number) row[6]).longValue());
                    dto.setElectionName((String) row[7]);

                    return dto;
                })
                .toList();
    }
    
   
    

}

