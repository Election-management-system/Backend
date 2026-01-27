package com.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailRequestDto {

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Please provide a valid recipient email address")
    private String to;

    @NotBlank(message = "Email subject is required")
    @Size(max = 200, message = "Subject must not exceed 200 characters")
    private String subject;

    @NotBlank(message = "Email body is required")
    @Size(max = 5000, message = "Email body must not exceed 5000 characters")
    private String body;
}