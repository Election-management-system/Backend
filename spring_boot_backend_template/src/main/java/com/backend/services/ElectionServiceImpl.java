package com.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.ElectionCreateDTO;
import com.backend.dtos.ElectionResponseDTO;
import com.backend.entities.Election;
import com.backend.exception.BadRequestException;
import com.backend.exception.BusinessRuleException;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.ElectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ElectionResponseDTO> getUpcomingElections() {

        LocalDate today = LocalDate.now();

        List<Election> elections =
                electionRepository
                        .findByElectionDateGreaterThanEqualOrderByElectionDateAsc(today);
        

        return elections.stream()
                .map(election ->
                        modelMapper.map(election, ElectionResponseDTO.class))
                .toList();
    }

    @Override
    public List<ElectionResponseDTO> getAllElections() {

        return electionRepository.findAll()
                .stream()
                .map(election ->
                        modelMapper.map(election, ElectionResponseDTO.class))
                .toList();
    }

    @Override
    public String createElection(ElectionCreateDTO dto) {

        // 1️⃣ Validate date flow (INVALID INPUT)
        if (dto.getNominationStartDate().isAfter(dto.getNominationEndDate())) {
            throw new BadRequestException(
                    "Nomination start date cannot be after nomination end date");
        }

        if (dto.getNominationEndDate().isAfter(dto.getCampaignEndDate())) {
            throw new BadRequestException(
                    "Nomination end date cannot be after campaign end date");
        }

        if (dto.getCampaignEndDate().isAfter(dto.getElectionDate())) {
            throw new BadRequestException(
                    "Campaign end date cannot be after election date");
        }

        // 2️⃣ Map DTO → Entity
        Election election = new Election();
        election.setElectionName(dto.getElectionName());
        election.setElectionPost(dto.getElectionPost());
        election.setElectionDate(dto.getElectionDate());
        election.setNominationStartDate(dto.getNominationStartDate());
        election.setNominationEndDate(dto.getNominationEndDate());
        election.setCampaignEndDate(dto.getCampaignEndDate());
        election.setElectionNorms(dto.getElectionNorms());

        // 3️⃣ Default status
        election.setIsactive(false); // admin activates later

        electionRepository.save(election);

        return "Election created successfully";
    }

    @Override
    public void autoCloseIfExpired(Election election) {

        if (election.isIsactive()
                && election.getElectionDate().isBefore(LocalDate.now())) {

            election.setIsactive(false);
            electionRepository.save(election);
        }
    }

    @Override
    public String activateElection(Long electionId) {

        Election election = electionRepository.findById(electionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Election not found with ID: " + electionId)
                );

        // BUSINESS RULES
        if (election.isIsactive()) {
            throw new BusinessRuleException("Election is already active");
        }

        LocalDate today = LocalDate.now();

        if (today.isBefore(election.getNominationStartDate())) {
            throw new BusinessRuleException(
                    "Election cannot be activated before nomination start date");
        }

        if (today.isAfter(election.getElectionDate())) {
            throw new BusinessRuleException(
                    "Election date is over, cannot activate");
        }

        election.setIsactive(true);
        electionRepository.save(election);

        return "Election activated successfully";
    }
}
