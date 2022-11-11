package com.example.challenge.s1_challenge.controller;


import com.example.challenge.s1_challenge.domain.User;
import com.example.challenge.s1_challenge.exception.UserNotFoundException;
import com.example.challenge.s1_challenge.service.SecurityTokenGenerator;
import com.example.challenge.s1_challenge.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private UserServiceImpl userServiceImpl;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, SecurityTokenGenerator securityTokenGenerator) {
        this.userServiceImpl = userServiceImpl;
        this.securityTokenGenerator = securityTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        ResponseEntity responseEntity = null;
        Map<String,String> map = null;
        try {
            User user1 = userServiceImpl.findByUsernameAndPassword(user.getUsername(),user.getPassword());
            if (user1.getUsername().equals(user.getUsername())){
                map = securityTokenGenerator.generateToken(user);
            }
            responseEntity = new ResponseEntity<>(map,HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception se) {
            responseEntity = new ResponseEntity<>("Try After sometimes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        return new ResponseEntity<>(userServiceImpl.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/userdata/v1/fetch")
    public ResponseEntity<?> getUser(){

        return new ResponseEntity<>(userServiceImpl.getAllUser(),HttpStatus.OK);
    }

    @DeleteMapping("/userdata/v1/{userId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable ("userId") int userId) throws UserNotFoundException {
        ResponseEntity responseEntity = null;
        try {
            userServiceImpl.deleteUser(userId);
            responseEntity = new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
