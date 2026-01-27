package com.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRegisterDTO {

  @NotNull(message = "Voter ID is required")
  private Long voterId;

  @NotNull(message = "Election ID is required")
  private Long electionId;

  @NotBlank(message = "Party name is required")
  @Size(min = 2, max = 100, message = "Party name must be between 2 and 100 characters")
  private String partyName;

  @Size(max = 2000, message = "Manifesto must not exceed 2000 characters")
  private String manifesto; // optional

  // private byte[] partyLogo;
}
