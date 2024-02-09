package com.demo.exceptions;

public class ExchangeValidationException extends RuntimeException {
    public ExchangeValidationException(String message) {
        super(message);
    }
}
