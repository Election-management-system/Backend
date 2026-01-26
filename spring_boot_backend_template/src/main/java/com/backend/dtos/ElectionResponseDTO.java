package com.backend.dtos;


import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ElectionResponseDTO {

    private Long id;
    private String electionName;
    private String electionPost;
    private LocalDate electionDate;
}
