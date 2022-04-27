package com.example.educationapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class AppUser implements UserDetails {
    private final AppUserDetails appUserDetails;

    public AppUser(AppUserDetails appUserDetails) {
        this.appUserDetails = appUserDetails;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return appUserDetails.getRoles().getGrantedAuth();
    }

    @Override
    public String getPassword() {
        return appUserDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return appUserDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return appUserDetails.getActivate();
    }
}
