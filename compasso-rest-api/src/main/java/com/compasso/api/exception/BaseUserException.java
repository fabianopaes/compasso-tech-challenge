package com.compasso.api.exception;

public class BaseUserException extends RuntimeException{

    private String id;

    public BaseUserException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
