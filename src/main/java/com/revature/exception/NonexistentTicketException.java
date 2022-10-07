package com.revature.exception;

public class NonexistentTicketException extends  Exception {

    public  NonexistentTicketException(String message) {
        super (message);
    }
}
