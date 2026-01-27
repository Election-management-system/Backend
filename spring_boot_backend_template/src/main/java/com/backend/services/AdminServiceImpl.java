package com.backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.AdminRegisterDTO;
import com.backend.entities.Admin;
import com.backend.exception.BadRequestException;
import com.backend.repository.AdminRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerAdmin(AdminRegisterDTO dto) {

        // 1️⃣ Check email uniqueness
        if (adminRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException(
                "Admin already exists with email: " + dto.getEmail()
            );
        }

        // 2️⃣ Create admin
        Admin admin = new Admin();
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());

        // 🔐 Hash password
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 🔐 Assign role
        admin.setRole("ROLE_ADMIN");

        adminRepository.save(admin);

        return "Admin registered successfully";
    }
}
