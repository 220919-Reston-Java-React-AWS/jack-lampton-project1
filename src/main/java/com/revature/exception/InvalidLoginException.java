package com.revature.exception;

public class InvalidLoginException extends Exception { //receives all the ordinary properties of an exception

    public InvalidLoginException(String message) {
        super(message);
    }
}
