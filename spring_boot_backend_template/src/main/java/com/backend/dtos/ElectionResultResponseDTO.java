package com.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectionResultResponseDTO {
	
    private Long electionId;
    private Long candidateId;
    private String partyName;
    private Long totalVotes;
    
}

