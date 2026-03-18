package com.pao.laboratory03.exceptions;

public class InvalidAgeException extends RuntimeException{
    private final String message;

    public InvalidAgeException(String msg){
        super("Varsta de " + msg + "  este invalida");
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
