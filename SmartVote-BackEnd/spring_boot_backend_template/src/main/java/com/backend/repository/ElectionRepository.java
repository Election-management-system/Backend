package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.Election;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findByIsActiveTrue();
}
