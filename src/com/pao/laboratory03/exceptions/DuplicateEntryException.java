package com.pao.laboratory03.exceptions;

public class DuplicateEntryException extends RuntimeException{
    private String message;

    public    DuplicateEntryException(String msg){
        super("Entry-ul " + msg + "  deja exista");
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
