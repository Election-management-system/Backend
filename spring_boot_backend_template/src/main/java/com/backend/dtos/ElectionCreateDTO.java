package com.backend.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectionCreateDTO {

    @NotBlank(message = "Election name is required")
    @Size(min = 3, max = 100, message = "Election name must be between 3 and 100 characters")
    private String electionName;

    @NotBlank(message = "Election post is required")
    @Size(min = 2, max = 100, message = "Election post must be between 2 and 100 characters")
    private String electionPost;

    @NotNull(message = "Election date is required")
    @FutureOrPresent(message = "Election date must be today or in the future")
    private LocalDate electionDate;

    @NotNull(message = "Nomination start date is required")
    private LocalDate nominationStartDate;

    @NotNull(message = "Nomination end date is required")
    private LocalDate nominationEndDate;

    @NotNull(message = "Campaign end date is required")
    private LocalDate campaignEndDate;

    @NotBlank(message = "Election norms are required")
    @Size(max = 5000, message = "Election norms must not exceed 5000 characters")
    private String electionNorms;
}
