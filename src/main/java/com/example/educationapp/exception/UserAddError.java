package com.example.educationapp.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserAddError {
    @Getter
    @Setter
    String status;

    @Setter
    @Getter
    String message;
}
