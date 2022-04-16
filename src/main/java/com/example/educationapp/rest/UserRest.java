package com.example.educationapp.rest;

import com.example.educationapp.entity.User;
import com.example.educationapp.exception.UserNotFoundError;
import com.example.educationapp.exception.UserNotFoundException;
import com.example.educationapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<String> addUser(@RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<String>("User added.",HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    User getUser(@PathVariable int id){
        return userRepository
                .findById(id)
                .orElseThrow(() ->new UserNotFoundException("User Not Found"));
    }

    @DeleteMapping("/delUser/{id}")
    ResponseEntity<String> delUser(@PathVariable int id){
        userRepository.delete(userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found")));
        return new ResponseEntity<String>("User Deleted",HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<UserNotFoundError> handleUserNotFound(UserNotFoundException exc){
        return new ResponseEntity<UserNotFoundError>(
                new UserNotFoundError("error", exc.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
