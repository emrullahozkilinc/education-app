package com.example.educationapp.exception;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class UserAddError {
    String status;
    List<String> message;
}
