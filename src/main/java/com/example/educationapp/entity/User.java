package com.example.educationapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.example.educationapp.enums.Type;
import com.example.educationapp.conf.*;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "users")
@TypeDef(name = "usertype", typeClass = PostgreSQLEnumType.class)
public class User {
    @Setter
    @Getter
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.Type(type = "usertype")
    private Type type;
}