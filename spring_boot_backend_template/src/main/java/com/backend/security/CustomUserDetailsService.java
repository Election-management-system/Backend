package com.backend.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.backend.repository.AdminRepository;
import com.backend.repository.VoterRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final VoterRepository voterRepository;

    public CustomUserDetailsService(AdminRepository adminRepository,
                                    VoterRepository voterRepository) {
        this.adminRepository = adminRepository;
        this.voterRepository = voterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        return ( adminRepository.findByEmail(email))
                .map(admin ->
                        new CustomUserDetails(
                                admin.getEmail(),
                                admin.getPassword(),
                                admin.getRole()
                        )
                )
                .orElseGet(() ->
                        voterRepository.findByEmail(email)
                                .map(voter ->
                                        new CustomUserDetails(
                                                voter.getEmail(),
                                                voter.getPassword(),
                                                voter.getRole()
                                        )
                                )
                                .orElseThrow(() ->
                                        new UsernameNotFoundException("User not found"))
                );
    }
}
