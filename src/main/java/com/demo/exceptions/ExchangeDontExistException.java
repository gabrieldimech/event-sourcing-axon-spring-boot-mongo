package com.demo.exceptions;

public class ExchangeDontExistException extends RuntimeException {
    public ExchangeDontExistException(String message) {
        super(message);
    }
}
