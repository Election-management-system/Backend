package com.backend.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinnerResponseDTO {

    private Long electionId;
    private String electionName;

    private Long candidateId;
    
    private String partyName;
    private Long totalVotes;
}

