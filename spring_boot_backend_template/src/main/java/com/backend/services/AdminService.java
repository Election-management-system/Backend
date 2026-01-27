package com.backend.services;

import com.backend.dtos.AdminRegisterDTO;

public interface AdminService {

    String registerAdmin(AdminRegisterDTO dto);
}
