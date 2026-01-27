package com.backend.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.PendingVoterResponseDTO;
import com.backend.dtos.VoterRegisterDTO;
import com.backend.dtos.VoterResponseDTO;
import com.backend.entities.Voter;
import com.backend.exception.BadRequestException;
import com.backend.exception.BusinessRuleException;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.VoterRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {

    private final VoterRepository voterRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<VoterResponseDTO> getAllVoters() {

        return voterRepository.findAll()
                .stream()
                .map(voter -> modelMapper.map(voter, VoterResponseDTO.class))
                .toList();
    }

    @Override
    public VoterResponseDTO registerVoter(VoterRegisterDTO dto) {

        // 🔒 Optional but RECOMMENDED uniqueness checks
        if (voterRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException(
                    "Voter already exists with email: " + dto.getEmail());
        }

        if (voterRepository.existsByAadharCardNo(dto.getAadharCardNo())) {
            throw new BadRequestException(
                    "Voter already exists with Aadhar number");
        }

        Voter voter = new Voter();

        voter.setFirstName(dto.getFirstName());
        voter.setLastName(dto.getLastName());
        voter.setEmail(dto.getEmail());
        voter.setPassword(passwordEncoder.encode(dto.getPassword()));
        voter.setDOB(dto.getDOB());
        voter.setAadharCardNo(dto.getAadharCardNo());
        voter.setAddress(dto.getAddress());
        voter.setMobileNo(dto.getMobileNo());

        voter.setApproved(false);
       // voter.setVoted(false);
        voter.setRole("ROLE_VOTER");

        voterRepository.save(voter);

        return modelMapper.map(voter, VoterResponseDTO.class);
    }

    @Override
    public VoterResponseDTO getVoterById(Long voterId) {

        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter not found with ID: " + voterId)
                );

        return modelMapper.map(voter, VoterResponseDTO.class);
    }

    @Override
    public String updateVoter(Long voterId, @Valid VoterRegisterDTO voterDto) {

        Voter existingVoter = voterRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter not found with ID: " + voterId)
                );

        modelMapper.map(voterDto, existingVoter);

        // 🔐 Re-hash password if updated
        if (voterDto.getPassword() != null) {
            existingVoter.setPassword(
                    passwordEncoder.encode(voterDto.getPassword()));
        }

        voterRepository.save(existingVoter);

        return "Voter updated successfully with ID: " + voterId;
    }

    @Override
    public String deleteVoter(Long voterId) {

        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter not found with ID: " + voterId)
                );

        // Soft delete
        voter.setApproved(false);
        voterRepository.save(voter);

        return "Voter deleted successfully with ID: " + voterId;
    }

    @Override
    public String approveVoter(Long voterId) {

        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter not found with ID: " + voterId)
                );

        if (voter.isApproved()) {
            throw new BusinessRuleException(
                    "Voter is already approved");
        }

        voter.setApproved(true);
        voterRepository.save(voter);

        return "Voter approved successfully";
    }

    @Override
    public List<PendingVoterResponseDTO> getPendingVoters() {

        return voterRepository.findAllPendingVoters()
                .stream()
                .map(voter -> {
                    PendingVoterResponseDTO dto =
                            new PendingVoterResponseDTO();

                    dto.setVoterId(voter.getId());
                    dto.setFirstName(voter.getFirstName());
                    dto.setLastName(voter.getLastName());
                    dto.setEmail(voter.getEmail());
                    dto.setMobileNo(voter.getMobileNo());
                    dto.setAddress(voter.getAddress());
                    dto.setDob(voter.getDOB());

                    return dto;
                })
                .toList();
    }
}
