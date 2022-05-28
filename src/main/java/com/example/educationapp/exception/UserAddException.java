package com.example.educationapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAddException extends RuntimeException{
    public UserAddException(String message){super(message);}
}
