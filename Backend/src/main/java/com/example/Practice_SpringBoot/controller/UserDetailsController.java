package com.example.Practice_SpringBoot.controller;


import com.example.Practice_SpringBoot.Exception.UserNotFoundException;
import com.example.Practice_SpringBoot.entity.UserDetails;
import com.example.Practice_SpringBoot.service.UserDetailsService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class UserDetailsController {
    @Autowired
    public UserDetailsService userDetailsService;

    @PostMapping("/api/user/register")
    public ResponseEntity<String> registeruser(@RequestParam String userName,
                                               @RequestParam String passwordHash,
                                               @RequestParam String role,
                                               @RequestParam String email,
                                               @RequestParam String phoneNumber,
                                               @RequestParam boolean isActive) {
        userDetailsService.saveUserDeatils(new UserDetails(userName, passwordHash, role, email, phoneNumber, isActive));
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> loginUser(
            @RequestParam("userName") String userName,
            @RequestParam("passwordHash") String passwordHash
    ) {
        // Authenticate user credentials
        boolean isAuthenticated = userDetailsService.authenticateUser(userName, passwordHash);

        if (isAuthenticated) {
            // Fetch user details from the database
            UserDetails user = userDetailsService.getUserByUserName(userName);

            if (user != null) {
                // Prepare the response map
                Map<String, Object> response = new HashMap<>();
                response.put("userId", user.getUserId());
                response.put("userName", user.getUserName());
                response.put("role", user.getRole()); // Include role in the response
                response.put("message", "Login successful!");

                // Return the response
                return ResponseEntity.ok(response);
            } else {
                // If user details are not found
                return ResponseEntity.status(404).body("User details not found.");
            }
        }
        // If authentication fails
        return ResponseEntity.status(401).body("Invalid credentials.");
    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity<UserDetails> getByUserId(@PathVariable Long userId) {
        UserDetails userDetails = userDetailsService.getByUserId(userId);
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userDetails);
    }




}
