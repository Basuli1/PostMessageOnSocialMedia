package com.example.SocialMedia.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRegistrationException extends RuntimeException{
    
    public InvalidRegistrationException(String message) {
        super(message);
    }

}