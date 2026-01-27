package com.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.dtos.EmailRequestDto;

@Service
public class EmailClient {

    private final RestTemplate restTemplate;

    public EmailClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendVoterApprovedEmail(String email, String name) {

        EmailRequestDto dto = new EmailRequestDto();
        dto.setTo(email);
        dto.setSubject("Voter Approval Successful");
        dto.setBody("""
            <h3>Hello %s,</h3>
            <p>Your voter registration has been <b>approved successfully</b>.</p>
            <p>You are now eligible to vote.</p>
            <br/>
            <p>Online Voting System</p>
        """.formatted(name));

        restTemplate.postForObject(
            "http://localhost:5035/api/email/send",
            dto,
            String.class
        );
    }
}
