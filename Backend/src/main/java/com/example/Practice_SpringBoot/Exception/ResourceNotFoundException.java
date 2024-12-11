package com.example.Practice_SpringBoot.Exception;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
