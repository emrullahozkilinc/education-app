package com.example.educationapp.jwt;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserPassAuthReq {
    private String username;
    private String password;
}
