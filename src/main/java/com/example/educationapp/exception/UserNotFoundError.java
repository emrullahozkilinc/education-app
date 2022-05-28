package com.example.educationapp.exception;

import lombok.*;

@Data
@AllArgsConstructor
public class UserNotFoundError {
    String status;
    String message;
}
