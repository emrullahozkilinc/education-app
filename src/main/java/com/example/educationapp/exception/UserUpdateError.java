package com.example.educationapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserUpdateError {
    String status;
    List<String> message;
}
