package com.compasso.api.exception;

public class ResourceNotFoundException extends BaseUserException {

    public ResourceNotFoundException(String message, String state) {
        super(message, state);
    }

}
