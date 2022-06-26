package com.example.educationapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.example.educationapp.enums.Type;
import com.example.educationapp.conf.*;
import org.hibernate.annotations.TypeDef;

@ApiModel(value = "User model in database.", description = "This model is used to store user data in database.")
@Getter
@Setter
@Entity
@Table(name = "users")
@TypeDef(name = "usertype", typeClass = PostgreSQLEnumType.class)
public class User {
    @ApiModelProperty(value = "User id (auto generated)", example = "1")
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "User name", example = "John")
    @NotBlank(message = "Name cannot be blank.")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "User username", example = "john", required = true)
    @NotBlank(message = "Username cannot be blank.")
    @Column(name = "username", nullable = false)
    private String username;

    @ApiModelProperty(value = "User password", example = "123456", required = true)
    @NotBlank(message = "Password cannot be blank.")
    @Column(name = "password", nullable = false)
    private String password;

    @ApiModelProperty(value = "User type. Matches with Type enum.", example = "student, admin")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    @org.hibernate.annotations.Type(type = "usertype")
    private Type type;
}