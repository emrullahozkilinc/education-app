package com.example.educationapp.auth;

import com.example.educationapp.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AppUserSevice implements UserDetailsService {

    private final AppUserDetailsRepo appUserDetailsRepo;

    public AppUserSevice(AppUserDetailsRepo appUserDetailsRepo) {
        this.appUserDetailsRepo = appUserDetailsRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if(appUserDetailsRepo.findByUsername(username) == null)
            throw new UserNotFoundException("This user not registered.");
        else
            return new AppUser(appUserDetailsRepo.findByUsername(username));
    }
}
