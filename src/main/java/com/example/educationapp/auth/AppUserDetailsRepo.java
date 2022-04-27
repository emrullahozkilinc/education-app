package com.example.educationapp.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDetailsRepo extends JpaRepository<AppUserDetails, Integer> {
    AppUserDetails findByUsername(String username);
}