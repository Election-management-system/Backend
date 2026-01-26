package com.backend.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectionCreateDTO {

    @NotBlank
    private String electionName;

    @NotBlank
    private String electionPost;

    @NotNull
    private LocalDate electionDate;

    @NotNull
    private LocalDate nominationStartDate;

    @NotNull
    private LocalDate nominationEndDate;

    @NotNull
    private LocalDate campaignEndDate;

    @NotBlank
    private String electionNorms;
}
