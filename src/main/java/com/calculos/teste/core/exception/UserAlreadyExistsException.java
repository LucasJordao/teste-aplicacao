package com.calculos.teste.core.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomException{
    public UserAlreadyExistsException() {
        super("User already exists", HttpStatus.CONFLICT);
    }
}
