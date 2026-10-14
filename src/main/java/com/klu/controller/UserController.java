package com.klu.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klu.login.*;
import com.klu.model.User;
import com.klu.repo.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login-db")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request) {

        Optional<User> result =
                userRepository.findByUsername(request.getUsername());

        if (result.isPresent()
                && result.get().getPassword()
                .equals(request.getPassword())) {

            return ResponseEntity.ok(
                    "Authentication Successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authentication Failed");
    }
}