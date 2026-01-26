package com.backend.services;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.VoterRegisterDTO;
import com.backend.dtos.VoterResponseDTO;
import com.backend.entities.Voter;
import com.backend.repository.VoterRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@Service
@Transactional
@RequiredArgsConstructor
public class VoterServiceImpl  implements VoterService{
	
	//dependency
	@Autowired
	private final VoterRepository voterRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<VoterResponseDTO> getAllVoters() {

		
		return voterRepository.findAll()
				.stream()
				.map(voter -> modelMapper.map(voter, VoterResponseDTO.class))
				.toList();
				
	}

	@Override
	public String registerVoter(VoterRegisterDTO voter) {
	    String msg = "Voter Registration Failed";

	    // DTO → Entity conversion
	    Voter newVoter = modelMapper.map(voter, Voter.class);

	    // Save entity
	    Voter savedVoter = voterRepository.save(newVoter);

	    // Check save success
	    if (savedVoter != null ) 
	        msg = "Voter Registered Successfully";
	    return msg;
	}

	@Override
	public VoterResponseDTO getVoterById(Long voterId) {
		// TODO Auto-generated method stub
		
		Voter voter =  voterRepository.findById(voterId).orElse(null);
		
		VoterResponseDTO newVoter = modelMapper.map(voter, VoterResponseDTO.class);
		
		return newVoter;
		
	}

	@Override
	public String updateVoter(Long voterId, @Valid VoterRegisterDTO voterDto) {

	    // 1. Fetch existing voter (MANDATORY)
	    Voter existingVoter = voterRepository.findById(voterId)
	            .orElseThrow(() ->
	                    new RuntimeException("Voter not found with ID: " + voterId)
	            );

	    // 2. Map DTO → existing entity
	    modelMapper.map(voterDto, existingVoter);

	    // 3. Save updated voter
	    voterRepository.save(existingVoter);

	    return "Voter updated successfully with ID: " + voterId;
	}


	@Override
	public String deleteVoter(Long voterId) {

	    Voter voter = voterRepository.findById(voterId)
	            .orElseThrow(() ->
	                    new RuntimeException("Voter not found with ID: " + voterId)
	            );

	    voter.setApproved(false); // soft delete
	    voterRepository.save(voter);

	    return "Voter deleted successfully with ID: " + voterId;
	}

	
	


}
