package com.pao.project.foodDelivery.exception;

public class StocInsuficientException extends Exception {
    public StocInsuficientException(String message) {
        super(message);
    }

    public StocInsuficientException(String message, Throwable cause) {
        super(message, cause);
    }
}
