package com.backend.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingVoterResponseDTO {

    private Long voterId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String address;
    private LocalDate dob;
}

