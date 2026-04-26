package com.pao.project.foodDelivery.exception;

public class ComandaNegasitaException extends Exception {
    public ComandaNegasitaException(String message) {
        super(message);
    }

    public ComandaNegasitaException(String message, Throwable cause) {
        super(message, cause);
    }
}
