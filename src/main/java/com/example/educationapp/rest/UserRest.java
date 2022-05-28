package com.example.educationapp.rest;

import com.example.educationapp.entity.User;
import com.example.educationapp.exception.*;
import com.example.educationapp.repos.UserRepository;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserRest {

    UserRepository userRepository;

    UserRest(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('user:read')")
    List<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('user:write')")
    ResponseEntity<String> addUser(@Valid @RequestBody User user){
        userRepository.save(user);
        return new ResponseEntity<>("User added.",HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    ResponseEntity<String> updateUser(@Valid @RequestBody User user, @PathVariable int id){
        userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        userRepository.updateUserById(id, user);
        return new ResponseEntity<>("User Updated", HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    User getUser(@PathVariable int id){
        return userRepository
                .findById(id)
                .orElseThrow(() ->new UserNotFoundException("User Not Found"));
    }

    @DeleteMapping("/delUser/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
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

    @ExceptionHandler
    public ResponseEntity<UserUpdateError> handleUpdateUser(UserUpdateException exc){
        System.err.println("sdvsd");
        List<String> excs = new ArrayList<>(Collections.singleton(exc.getMessage()));
                /*
                .getBindingResult()
                .getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toCollection(LinkedList::new));
                */
        return new ResponseEntity<>(
                new UserUpdateError("error", excs),
                HttpStatus.BAD_REQUEST
        );
    }
}
