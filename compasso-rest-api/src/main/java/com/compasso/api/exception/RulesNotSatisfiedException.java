package com.compasso.api.exception;

public class RulesNotSatisfiedException extends BaseUserException {

    public RulesNotSatisfiedException(String message, String state) {
        super(message, state);
    }

}
