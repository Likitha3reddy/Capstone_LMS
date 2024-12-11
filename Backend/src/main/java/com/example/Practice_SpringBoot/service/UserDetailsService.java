package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.UserDetails;
import com.example.Practice_SpringBoot.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService {
    @Autowired
    private  UserDetailsRepository userDetailsRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordEncoder passwordencoder;




    public UserDetails saveUserDeatils(UserDetails userDetails){
        UserDetails signupUser=new UserDetails();
        signupUser.setUserName(userDetails.getUserName());
        signupUser.setRole(userDetails.getRole());
        signupUser.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
        signupUser.setEmail(userDetails.getEmail());
        signupUser.setPhoneNumber(userDetails.getPhoneNumber());
        signupUser.setActive(userDetails.getActive());
        return userDetailsRepository.save(signupUser);
    }


    public boolean authenticateUser(String username, String password) {
        UserDetails user = userDetailsRepository.findByUserName(username);
        if (user != null && passwordencoder.matches(password, user.getPasswordHash())) {
            return true; // User authenticated successfully
        }
        return false; // Authentication failed
    }


    public UserDetails getByUserId(Long userId) {
        return userDetailsRepository.findById(userId).orElse(null);
    }

    public UserDetails getUserByUserName(String userName) {
        return userDetailsRepository.findByUserName(userName);
    }
}
