package com.calculos.teste.core.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
