package com.example.educationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CustomPages {

    @GetMapping("login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("users")
    public String userPage(){
        return "Users";
    }
}
