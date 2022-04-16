package com.example.educationapp.exception;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserAddError {
    @Getter
    @Setter
    String status;

    @Getter
    @Setter
    List<String> message;
}
