package com.example.educationapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.example.educationapp.enums.Type;
import com.example.educationapp.conf.*;
import org.hibernate.annotations.TypeDef;

@Getter
@Setter
@Entity
@Table(name = "users")
@TypeDef(name = "usertype", typeClass = PostgreSQLEnumType.class)
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Username cannot be blank.")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.Type(type = "usertype")
    private Type type;
}