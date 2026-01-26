package com.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRegisterDTO {

    @NotNull
    private Long voterId;

    @NotNull
    private Long electionId;

    @NotBlank
    private String partyName;

    private String manifesto;   // optional
    
    private byte[] partyLogo;
}
