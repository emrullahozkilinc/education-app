package com.example.educationapp.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.educationapp.enums.UserPermissions.*;

public enum UserRoles {
    STUDENT(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(USER_WRITE, USER_READ, USER_DELETE, USER_UPDATE));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions){
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuth() {
        Set<SimpleGrantedAuthority> auths = getPermissions().stream()
                .map(per -> new SimpleGrantedAuthority(per.getPermission()))
                .collect(Collectors.toSet());
        auths.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return auths;
    }
}
