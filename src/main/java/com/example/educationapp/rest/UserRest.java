package com.example.educationapp.rest;

import com.example.educationapp.entity.User;
import com.example.educationapp.exception.UserAddError;
import com.example.educationapp.exception.UserAddException;
import com.example.educationapp.exception.UserNotFoundError;
import com.example.educationapp.exception.UserNotFoundException;
import com.example.educationapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    ResponseEntity<String> addUser(@Valid @RequestBody User user, BindingResult binRes){
        try{
            userRepository.save(user);
        }catch (Exception e){
            throw new UserAddException("User cannot add."+
                    binRes.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n")));
        }
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

    @ExceptionHandler
    public ResponseEntity<UserAddError> handleAddUser(UserAddException exc){
        return new ResponseEntity<UserAddError>(
                new UserAddError("error", exc.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
