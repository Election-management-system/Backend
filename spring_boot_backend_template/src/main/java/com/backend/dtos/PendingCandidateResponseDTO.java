package com.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingCandidateResponseDTO {

    private Long candidateId;

    private String partyName;
    private String manifesto;

    private Long voterId;
    private String voterName;
    private String voterEmail;

    private Long electionId;
    private String electionName;
}
