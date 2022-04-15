package com.example.educationapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.example.educationapp.enums.Type;

@Entity
@Table(name = "users")
public class User {
    @Setter
    @Getter
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Setter
    @Getter
    @Column(name = "name")
    private String name;

    @Setter
    @Getter
    @Column(name = "username", nullable = false)
    private String username;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(name = "type", nullable = false)
    @Enumerated
    private Type type;
}