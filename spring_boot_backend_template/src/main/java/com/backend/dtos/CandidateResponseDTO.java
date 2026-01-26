package com.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateResponseDTO {

    private Long candidateId;

    private String partyName;
    private String manifesto;
    private boolean approved;

    // voter info
    private Long voterId;
    private String voterName;
    private String voterEmail;

    // election info
    private Long electionId;
    private String electionName;
}
