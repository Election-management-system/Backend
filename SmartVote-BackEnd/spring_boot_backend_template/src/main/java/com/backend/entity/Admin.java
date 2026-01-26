package com.backend.entity;

import java.time.LocalDateTime;

import com.backend.entity.enums.AdminRole;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String username;
    private String passwordHash;
    private String fullName;
    private String email;

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    private boolean isActive;

    private LocalDateTime createdAt = LocalDateTime.now();
}
