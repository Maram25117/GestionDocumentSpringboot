package com.example.Gestiondocument.controller;

import com.example.Gestiondocument.model.User;
import com.example.Gestiondocument.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getUsername().length() < 5 || user.getPassword().length() < 5) {
            return ResponseEntity.badRequest().body("Username ou password must be at least 5 characters long");
        }
        if (user.getPassword() == null || user.getUsername() == null ) {
            return ResponseEntity.badRequest().body("Password and Username cannot be null");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        }

        user.setRole("USER");
        userService.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    /*@PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser != null && user.getPassword().equals(foundUser.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }*/
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser != null && user.getPassword().equals(foundUser.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", foundUser.getId());
            response.put("role", foundUser.getRole()); // Renvoie également le rôle
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}

