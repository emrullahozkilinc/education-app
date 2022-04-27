package com.example.educationapp.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserSevice implements UserDetailsService {

    private final AppUserDetailsRepo appUserDetailsRepo;

    public AppUserSevice(AppUserDetailsRepo appUserDetailsRepo) {
        this.appUserDetailsRepo = appUserDetailsRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDetails appUserDetails = appUserDetailsRepo.findByUsername(username);
        return new AppUser(appUserDetails);
    }
}
