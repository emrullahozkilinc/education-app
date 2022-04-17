package com.example.educationapp.rest;

import com.example.educationapp.entity.User;
import com.example.educationapp.exception.UserAddError;
import com.example.educationapp.exception.UserNotFoundError;
import com.example.educationapp.exception.UserNotFoundException;
import com.example.educationapp.repos.UserRepository;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    ResponseEntity<String> addUser(@Valid @RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>("User added.",HttpStatus.OK);
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
        return new ResponseEntity<>("User Deleted",HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<UserNotFoundError> handleUserNotFound(UserNotFoundException exc){
        return new ResponseEntity<>(
                new UserNotFoundError("error", exc.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<UserAddError> handleAddUser(MethodArgumentNotValidException exc){

        List<String> excs = exc.getBindingResult()
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toCollection(LinkedList::new));

        return new ResponseEntity<>(
                new UserAddError("error", excs),
                HttpStatus.BAD_REQUEST
        );
    }
}
