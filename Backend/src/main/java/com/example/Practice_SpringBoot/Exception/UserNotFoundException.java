package com.example.Practice_SpringBoot.Exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String s) {
        super(s);
    }
}
