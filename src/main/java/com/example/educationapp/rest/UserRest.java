package com.example.educationapp.rest;

import com.example.educationapp.entity.User;
import com.example.educationapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRest {

    UserRepository userRepository;

    UserRest(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/getUsers")
    List<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/addUser")
    void addUser(@RequestBody User user){
        userRepository.save(user);
    }
}
