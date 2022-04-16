package com.example.educationapp.exception;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class UserNotFoundError {
    @Setter
    @Getter
    String status;
    @Setter
    @Getter
    String message;
}
