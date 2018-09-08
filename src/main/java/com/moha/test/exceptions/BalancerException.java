package com.moha.test.exceptions;

public class BalancerException extends Exception {

    private static final long serialVersionUID = 7989960030701074482L;

    public BalancerException(String message, Throwable cause) {
        super(message, cause);
    }
}
