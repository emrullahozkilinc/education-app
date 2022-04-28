package com.example.educationapp.auth;

import com.example.educationapp.conf.PostgreSQLEnumType;
import com.example.educationapp.conf.RolesEnumPostgreConf;
import com.example.educationapp.enums.UserRoles;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "user_management")
@TypeDef(name = "roles", typeClass = RolesEnumPostgreConf.class)
public class AppUserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "activate")
    private Boolean activate;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    @Type(type = "roles")
    private UserRoles roles;

    @Column(name = "username", nullable = false)
    private String username;
}