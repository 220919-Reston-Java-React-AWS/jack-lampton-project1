package com.revature.exception;

public class TicketAlreadyProcessedException extends Exception {

    public TicketAlreadyProcessedException(String message) {
        super(message);
    }
}
