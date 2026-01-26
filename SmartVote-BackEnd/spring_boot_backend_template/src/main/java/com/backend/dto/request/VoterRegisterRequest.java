package com.backend.dto.request;

import lombok.Data;

@Data
public class VoterRegisterRequest {
    private String studentOrMemberId;
    private String fullName;
    private String email;
    private String courseOrDepartment;
}
