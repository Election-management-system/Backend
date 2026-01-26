package com.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.ElectionCreateDTO;
import com.backend.dtos.ElectionResponseDTO;
import com.backend.repository.ElectionRepository;
import com.backend.entities.Election;


import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectionServiceImpl implements  ElectionService {

	@Autowired
	private  ElectionRepository electionRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Override
	public List<ElectionResponseDTO> getUpcomingElections() {

	    // 1. Get today's date
	    LocalDate today = LocalDate.now();

	    // 2. Fetch upcoming elections from DB
	    List<Election> elections =
	            electionRepository.findByElectionDateGreaterThanEqualAndIsactiveTrueOrderByElectionDateAsc(today);
	    
	    

	    // 3. Convert Entity → DTO
	    return elections.stream()
	            .map(election -> modelMapper.map(election, ElectionResponseDTO.class))
	            .toList();
	}



	@Override
	public List<ElectionResponseDTO> getAllElections() {
		 // 1. Fetch upcoming elections from DB
	    List<Election> elections =
	            electionRepository.findAll();
	    
	    // 2. Convert Entity → DTO
	    return elections.stream()
	            .map(election -> modelMapper.map(election, ElectionResponseDTO.class))
	            .toList();
	}



	@Override
	public String createElection(ElectionCreateDTO dto) {

	    // 1️⃣ Validate date flow
	    if (dto.getNominationStartDate().isAfter(dto.getNominationEndDate())) {
	        throw new RuntimeException("Nomination start date cannot be after nomination end date");
	    }

	    if (dto.getNominationEndDate().isAfter(dto.getCampaignEndDate())) {
	        throw new RuntimeException("Nomination end date cannot be after campaign end date");
	    }

	    if (dto.getCampaignEndDate().isAfter(dto.getElectionDate())) {
	        throw new RuntimeException("Campaign end date cannot be after election date");
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

        // check only if currently active
        if (election.isIsactive()
                && election.getElectionDate()
                           .isBefore(LocalDate.now())) {

            election.setIsactive(false);
            electionRepository.save(election);
        }
    }


	

}
