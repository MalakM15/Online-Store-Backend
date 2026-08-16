package com.myapp.cruddemo.controller;

import com.myapp.cruddemo.entity.User;
import com.myapp.cruddemo.dao.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*; 

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        System.out.println(">>> REGISTER CONTROLLER REACHED");

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        System.out.println("Password before encoding: " + user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRole(User.Role.CUSTOMER);
        System.out.println("Password after encoding: " + user.getPassword());

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.getFirstName());
    }

}
